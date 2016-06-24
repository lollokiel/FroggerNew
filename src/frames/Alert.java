package frames;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class Alert extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField alertPane;
	private JButton btnCloseWindow;

	public Alert(String alert) {

		this.setVisible(true);
		this.setTitle("Hinweis");
		this.setBounds(100, 100, 452, 159);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("30px"),
				FormSpecs.RELATED_GAP_ROWSPEC,}));
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				windowClose();
			}
			
		});
		
		
			alertPane = new JTextField();
			alertPane.setBackground(Color.getColor("eeeeee"));
			alertPane.setEditable(false);
			alertPane.setHorizontalAlignment(JTextField.CENTER);
			alertPane.setAlignmentY(JTextField.CENTER_ALIGNMENT);
			alertPane.setBorder(null);
			alertPane.setText(alert);
			this.getContentPane().add(alertPane, "2, 2, fill, fill");
		
			btnCloseWindow = new JButton("Schließen");
			btnCloseWindow.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					windowClose();
				}
			});
			this.getContentPane().add(btnCloseWindow, "2, 4, center, center");
	}
	
	/**
	 * Schließt das Mitteilungsfenster
	 */
	void windowClose() {
		this.dispose();
	}

}
