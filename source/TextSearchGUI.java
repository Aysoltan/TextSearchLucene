/* ************************************************************ 
 * Text Technology: SS2016
 * Project: Text Search with Lucene
 * Author: Aysoltan Gravina
 * 3142363
 * 
 * This is simple GUI for Text Search with Lucene.
 * It calls the indexSearcher() method form IndexSearch Class
 * and outputs the path to matched documents.
 * To show the content of document just click on it.
 * ***********************************************************/

package TextSearch;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;

public class TextSearchGUI {

	private JFrame frame;
	private JTextField textFieldQuery;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TextSearchGUI window = new TextSearchGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TextSearchGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Text Search With Lucene");
		frame.setBounds(100, 100, 850, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		/*** JList to output the results */
		JList listResults = new JList();

		listResults.addMouseListener(new MouseAdapter() {
			@Override
			/*** Shows the selected document */
			public void mouseClicked(MouseEvent e) {
				@SuppressWarnings("deprecation")
				/*** Gets the clicked Document */
				Object[] sel = listResults.getSelectedValues();
				for (int i = 0; i < sel.length; i++) {
					String clicked = sel[i].toString();
					String path = clicked.substring(clicked.indexOf("data"), clicked.length());
					/*** Gets the content of the Document and outputs it */
					try {
						FileReader fr = new FileReader(path);
						BufferedReader br = new BufferedReader(fr);
						String content = "";
						String line = "";
						while ((line = br.readLine()) != null) {
							content = content + line + "\n";
						}

						/***
						 * Shows the content of the document in separate window
						 */
						JTextArea textArea = new JTextArea(content);
						JScrollPane scrollPane = new JScrollPane(textArea);
						textArea.setLineWrap(true);
						textArea.setWrapStyleWord(true);
						scrollPane.setPreferredSize(new Dimension(600, 600));
						JOptionPane.showMessageDialog(null, scrollPane, path, JOptionPane.PLAIN_MESSAGE);
						br.close();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, e1);
					}

				}
			}
		});
		listResults.setBackground(new Color(245, 245, 245));
		listResults.setBounds(26, 187, 793, 385);
		frame.getContentPane().add(listResults);

		JLabel lblNewLabel = new JLabel("Founded Documents. To read the document click on it ...");
		lblNewLabel.setBounds(26, 158, 400, 27);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Enter Your Query:");
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblNewLabel_1.setBounds(26, 90, 139, 21);
		frame.getContentPane().add(lblNewLabel_1);

		/*** Field for Query */
		textFieldQuery = new JTextField();
		textFieldQuery.setBounds(164, 84, 489, 33);
		frame.getContentPane().add(textFieldQuery);
		textFieldQuery.setColumns(10);

		/***
		 * If the Button will be clicked, the method indexSearcher() will be
		 * called.
		 */
		JButton btnNewButton = new JButton("Search");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					/*** Gets the Search String */
					String query = textFieldQuery.getText();
					if (query.trim().length() > 0) {
						DefaultListModel dlm = new DefaultListModel();
						ArrayList<String> results = new ArrayList<String>();
						/***
						 * Calls the Method indexSearcher() from the class
						 * IndexSearch
						 */
						results = IndexSearch.indexSearcher(query);
						if (results.size() > 0) {
							for (Iterator<String> i = results.iterator(); i.hasNext();) {
								Object o = i.next();
								dlm.addElement(o.toString());
							}
							listResults.setModel(dlm);

						} else {
							JOptionPane.showMessageDialog(null, "No documents founded...");
						}

					}
				} catch (Exception except) {
					JOptionPane.showMessageDialog(null, except);
				}

			}

			private Container getContentPane() {
				// TODO Auto-generated method stub
				return null;
			}
		});
		btnNewButton.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		btnNewButton.setBounds(678, 84, 141, 33);
		frame.getContentPane().add(btnNewButton);
	}
}
