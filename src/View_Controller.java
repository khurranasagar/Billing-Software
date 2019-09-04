import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
//import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;

//import java.awt.event.KeyEvent;


public class View_Controller {

	private Invoice CurrentInvoice;
	
	private static int InvoiceNo;
	private static int Savenumber;
	
	private List<Product> AllProducts = new ArrayList<Product>();
	
	private JFrame HomeFrame = new JFrame();
	private GridBagConstraints c = new GridBagConstraints();
	
	// Main First Screen / Home Screen
	private JButton InvoiceMenuButton = new JButton("Invoice Menu");
	private JButton ItemMenuButton = new JButton("Item Menu");
	private JButton BackToHomeButton = new JButton("Back");
	private JButton OpenInvoiceButton = new JButton("Open Invoice");
	
	//Item Menu
	private JButton SearchItemButton = new JButton("Search Item");
	private JButton ViewAllItemsButton = new JButton("View All Items");
	private JButton AddItemButton = new JButton("Add Item");
	private JButton BackToItemMenuButton = new JButton("Back");
	
	//Invoice Menu
	private JButton CreateInvoiceButton = new JButton("Create Invoice");
	private JButton ShowAllInvoicesButton = new JButton("Show All Invoices");
	private JButton BackToInvoiceMenuButton = new JButton("Back");
	
	//ViewAllItems Screen
	private DefaultTableModel ViewAllItemsModel;
	private JTable ViewAllItemsTable;
	private JButton CreateItemPdf = new JButton("Create Pdf");
	
	//SearchItem Screen
	private JTextField SearchItem = new JTextField();
	private JButton SearchItemResultButton = new JButton("Search");
	private JTable SearchItemResults;
	private DefaultTableModel SearchResultsModel;
	private List<Product> SearchItemResultsList = new ArrayList<Product>();
	
	//AddItemScreen
	private JTextField ProductName;
	private JTextField MRP;
	private JTextField WholesalePrice;
	private JTextField RetailPrice;
	private JLabel AddItemHeaderLabel = new JLabel();
	private JButton AddingItemButton = new JButton("Add Item");
	
	//CreateInvoice Screen
	private JTextField CustomerNameField = new JTextField("");
	private JTextField ContactField = new JTextField("");
	private JTextField Address = new JTextField("");
	private JButton InvoiceCreationButton = new JButton("Create Invoice");
	private ButtonGroup radioGroup = new ButtonGroup();
	private JRadioButton rbutton1, rbutton2;
	
	//EditItem Screen
	private JButton EditNameButton = new JButton("Edit");
	private JButton EditMRPButton = new JButton("Edit");
	private JButton EditWholButton = new JButton("Edit");
	private JButton EditRetButton = new JButton("Edit");
	private JButton DeleteItemButton = new JButton("Delete");
	private Product EditProduct;
	private JButton BackToViewAllItemsButton = new JButton("Back");
	
	//Invoice Creation Screen
	private JTextField SearchItemForInvoice = new JTextField();
	private JButton SearchItemInvoiceButton = new JButton("Search");
	private JTable SearchResultsTableInvoice;
	private DefaultTableModel SearchResultTableModel;
	private List<Product> SearchItemInvoiceList = new ArrayList<Product>();
	private JTable InvoiceItemsTable;
	private DefaultTableModel InvoiceItemsModel;
	private JButton BackToInvoiceCreation = new JButton("Back");
	private JButton CreateInvoiceComplete = new JButton("Create Invoice");
	private JLabel InvoiceTotalLabel = new JLabel("Invoice Total : 0.0");
	private JScrollPane scrollPane, scrollPane1;
	private listSelectionListener listenetSearchResults;
	private JLabel Remarks = new JLabel("Remarks : ");
	private JTextField RemarksField = new JTextField("");
	private JButton PakkaBill = new JButton("Pakka Bill");
	private ButtonGroup PaidButtonsGroup = new ButtonGroup();
	private JRadioButton PaidButton, NotPaidButton;
	private JTextField PaidAmountField = new JTextField("");
	
	
	@SuppressWarnings("rawtypes")
	private JComboBox SearchItemInvoiceComboBox;
	
	private KeyListener Searchkl;
	
	private String[] Product_Initials;
	private ArrayList<String> ProductInitialsList = new ArrayList<String>();
	
	//Others
	private DecimalFormat df = new DecimalFormat("#####0.00");
	private DecimalFormat df1 = new DecimalFormat("000000");
	private boolean BillType = false;
	
	private ListSelectionListener listenerSearchResults;
	
	private ListSelectionListener n;
	
	private ArrayList<Product> CurrentInvoiceItemList = new ArrayList<Product>();
	
	private Font font1 = new Font("SansSerif", Font.PLAIN, 16);
	
	private String outputFile;
	
	private JTextField ProductNumber = new JTextField("");
	
	//Constructor
	public View_Controller(){
		
		HomeFrame.setIconImage(new ImageIcon("D:/Workspace/Bill/icon.jpg").getImage());
		
		n = new newlistSelectionListener();
		
		CreateItemPdf.setForeground(Color.RED);
		CreateItemPdf.setBackground(new Color(42, 177, 249));
		CreateItemPdf.setFont(new Font("Arial", Font.PLAIN, 20));
		CreateItemPdf.addActionListener(new CreateItemsPdfAction());
		
		PakkaBill.setForeground(Color.RED);
		PakkaBill.setBackground(new Color(42, 177, 249));
		PakkaBill.setFont(new Font("Arial", Font.PLAIN, 20));
		PakkaBill.addActionListener(new PakkaBillAction());
		
		BackToInvoiceCreation.setForeground(Color.RED);
		BackToInvoiceCreation.setBackground(new Color(42, 177, 249));
		BackToInvoiceCreation.setFont(new Font("Arial", Font.PLAIN, 20));
		BackToInvoiceCreation.addActionListener(new BackToHomeAction());
		
		CreateInvoiceComplete.setForeground(Color.RED);
		CreateInvoiceComplete.setBackground(new Color(42, 177, 249));
		CreateInvoiceComplete.setFont(new Font("Arial", Font.PLAIN, 20));
		CreateInvoiceComplete.addActionListener(new CompleteCurrentInvoiceAction());
		
		InvoiceCreationButton.setForeground(Color.RED);
		InvoiceCreationButton.setBackground(new Color(42, 177, 249));
		InvoiceCreationButton.setFont(new Font("Arial", Font.PLAIN, 20));
		InvoiceCreationButton.addActionListener(new InvoiceCreationAction());
		
		BackToHomeButton.setForeground(Color.RED);
		BackToHomeButton.setBackground(new Color(42, 177, 249));
		BackToHomeButton.setFont(new Font("Arial", Font.PLAIN, 40));
		BackToHomeButton.addActionListener(new BackToHomeAction());
		
		OpenInvoiceButton.setForeground(Color.RED);
		OpenInvoiceButton.setBackground(new Color(42, 177, 249));
		OpenInvoiceButton.setFont(new Font("Arial", Font.PLAIN, 40));
		OpenInvoiceButton.addActionListener(new OpenInvoiceAction());
		
		BackToInvoiceMenuButton.setForeground(Color.RED);
		BackToInvoiceMenuButton.setBackground(new Color(42, 177, 249));
		BackToInvoiceMenuButton.setFont(new Font("Arial", Font.PLAIN, 20));
		BackToInvoiceMenuButton.addActionListener(new BackToHomeAction());
		
		BackToItemMenuButton.setForeground(Color.RED);
		BackToItemMenuButton.setBackground(new Color(42, 177, 249));
		BackToItemMenuButton.setFont(new Font("Arial", Font.PLAIN, 20));
		BackToItemMenuButton.addActionListener(new BackToItemMenuAction());
		
		AddingItemButton.setForeground(Color.RED);
		AddingItemButton.setBackground(new Color(42, 177, 249));
		AddingItemButton.setFont(new Font("Arial", Font.PLAIN, 20));
		AddingItemButton.addActionListener(new AddingItemAction());
		
		EditNameButton.setForeground(Color.RED);
		EditNameButton.setBackground(new Color(42, 177, 249));
		EditNameButton.setFont(new Font("Arial", Font.PLAIN, 20));
		EditNameButton.addActionListener(new EditNameAction());
		
		EditMRPButton.setForeground(Color.RED);
		EditMRPButton.setBackground(new Color(42, 177, 249));
		EditMRPButton.setFont(new Font("Arial", Font.PLAIN, 20));
		EditMRPButton.addActionListener(new EditMRPAction());
		
		EditWholButton.setForeground(Color.RED);
		EditWholButton.setBackground(new Color(42, 177, 249));
		EditWholButton.setFont(new Font("Arial", Font.PLAIN, 20));
		EditWholButton.addActionListener(new EditWholAction());
		
		EditRetButton.setForeground(Color.RED);
		EditRetButton.setBackground(new Color(42, 177, 249));
		EditRetButton.setFont(new Font("Arial", Font.PLAIN, 20));
		EditRetButton.addActionListener(new EditRetAction());
		
		DeleteItemButton.setForeground(Color.RED);
		DeleteItemButton.setBackground(new Color(42, 177, 249));
		DeleteItemButton.setFont(new Font("Arial", Font.PLAIN, 20));
		DeleteItemButton.addActionListener(new DeleteItemAction());
		
		BackToViewAllItemsButton.setForeground(Color.RED);
		BackToViewAllItemsButton.setBackground(new Color(42, 177, 249));
		BackToViewAllItemsButton.setFont(new Font("Arial", Font.PLAIN, 20));
		BackToViewAllItemsButton.addActionListener(new BackToViewAllItemsAction());
		
		InvoiceMenuButton.setForeground(Color.ORANGE);
		InvoiceMenuButton.setBackground(new Color(42, 177, 249));
		InvoiceMenuButton.addActionListener(new InvoiceMenuAction());
		InvoiceMenuButton.setFont(new Font("Arial", Font.PLAIN, 40));
		
		ItemMenuButton.setForeground(Color.ORANGE);
		ItemMenuButton.setBackground(new Color(42, 177, 249));
		ItemMenuButton.setFont(new Font("Arial", Font.PLAIN, 40));
		ItemMenuButton.addActionListener(new ItemMenuAction());
		
		CreateInvoiceButton.setForeground(Color.ORANGE);
		CreateInvoiceButton.setBackground(new Color(42, 177, 249));
		CreateInvoiceButton.addActionListener(new CreateInvoiceAction());
		CreateInvoiceButton.setFont(new Font("Arial", Font.PLAIN, 40));
		
		ShowAllInvoicesButton.setForeground(Color.ORANGE);
		ShowAllInvoicesButton.setBackground(new Color(42, 177, 249));
		ShowAllInvoicesButton.setFont(new Font("Arial", Font.PLAIN, 40));
		ShowAllInvoicesButton.addActionListener(new ShowAllInvoicesAction());
		
		SearchItemButton.setForeground(Color.ORANGE);
		SearchItemButton.setBackground(new Color(42, 177, 249));
		SearchItemButton.addActionListener(new SearchItemAction());
		SearchItemButton.setFont(new Font("Arial", Font.PLAIN, 40));
		
		SearchItemInvoiceButton.setForeground(Color.ORANGE);
		SearchItemInvoiceButton.setBackground(new Color(42, 177, 249));
		SearchItemInvoiceButton.addActionListener(new SearchItemInvoiceAction());
		SearchItemInvoiceButton.setFont(new Font("Arial", Font.PLAIN, 20));
		SearchItemInvoiceButton.setPreferredSize(new Dimension(300,100));
		
		AddItemButton.setForeground(Color.ORANGE);
		AddItemButton.setBackground(new Color(42, 177, 249));
		AddItemButton.setFont(new Font("Arial", Font.PLAIN, 40));
		AddItemButton.addActionListener(new AddItemAction());
		
		ViewAllItemsButton.setForeground(Color.ORANGE);
		ViewAllItemsButton.setBackground(new Color(42, 177, 249));
		ViewAllItemsButton.setFont(new Font("Arial", Font.PLAIN, 40));
		ViewAllItemsButton.addActionListener(new ViewAllItemsAction());
		
		SearchItemResultButton.setForeground(Color.RED);
		SearchItemResultButton.setBackground(new Color(42, 177, 249));
		SearchItemResultButton.setFont(new Font("Arial", Font.PLAIN, 20));
		SearchItemResultButton.addActionListener(new SearchItemResultAction());
		
		PaidButtonsGroup.add(PaidButton = new JRadioButton("Paid"));
		PaidButton.setName("Paid");
		PaidButtonsGroup.add(NotPaidButton = new JRadioButton("Not Paid"));
		NotPaidButton.setName("Not Paid");
		PaidButton.setBackground(Color.white);
		NotPaidButton.setBackground(Color.white);
		
		HomeFrame.setTitle("Billing Software");
		HomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		HomeFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
	
		AddItems();
//		homeScreen();
		Collections.sort(AllProducts, new Comparator<Product>() {
	        @Override
	        public int compare(final Product object1, final Product object2) {
	        return object1.getName().compareTo(object2.getName());
	        }
	    } );
		
		String[] columnNames = {"SNo","Product Name","MRP","Wholesale Price","Retail Price"};
		Object[][] data = new Object[0][5];
		
		SearchResultTableModel = new DefaultTableModel(data,columnNames);
		
		SearchResultsTableInvoice = new JTable(SearchResultTableModel);
		scrollPane = new JScrollPane(SearchResultsTableInvoice);
		
		ListSelectionModel cellSelectionModel = SearchResultsTableInvoice.getSelectionModel();
	    cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	    listenetSearchResults = new listSelectionListener();
	    cellSelectionModel.addListSelectionListener(listenetSearchResults) ;
		
	    

	    String[] InvoiceColumns = {"SNo.","Name","Quantity","Rate","Price"}; 
		    
	    Object[][] data1 = new Object[0][5];
		    
	    InvoiceItemsModel = new DefaultTableModel(data1,InvoiceColumns);
	    
	    InvoiceItemsTable = new JTable(InvoiceItemsModel);
	    scrollPane1 = new JScrollPane(InvoiceItemsTable);
	    
	    ListSelectionModel cellSelection = InvoiceItemsTable.getSelectionModel();
	    cellSelection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	    listenerSearchResults = new DeleteInvoiceItemListener();
	    cellSelection.addListSelectionListener(listenerSearchResults);
	    
	    InvoiceItemsTable.setRowSelectionAllowed(true);
	    InvoiceItemsTable.setColumnSelectionAllowed(false);
	    InvoiceItemsTable.setCellSelectionEnabled(true);
	    
	    SearchResultsTableInvoice.setRowSelectionAllowed(true);
		SearchResultsTableInvoice.setColumnSelectionAllowed(false);
		SearchResultsTableInvoice.setCellSelectionEnabled(true);
		
		ReadInitials();
		
		//Product_Initials = new String[] {"Ghari","Fena","Tops","Pickle","Phenyl","Surf","Excel","Ariel","Tresseme","Pantene"};
		
		SearchItemInvoiceComboBox = new AutoCompleteComboBox(Product_Initials);
		
		SearchItemInvoiceComboBox.setPreferredSize(new Dimension(250,80));
		
		Searchkl = new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				//System.out.println("F1");
				if(arg0.getKeyCode() == KeyEvent.VK_1) {
					System.out.println("F1");
					SearchItemForInvoice.requestFocus();
					SearchItemForInvoice.setText("");
				}
				if(arg0.getKeyCode() == KeyEvent.VK_2) {
					System.out.println("F2");
					ProductNumber.requestFocus();
					ProductNumber.setText("");
				}
				if(arg0.getKeyCode() == KeyEvent.VK_3) {
					System.out.println("F3");
					AddItemtoInvoice(Integer.parseInt(ProductNumber.getText().toString()) - 1);
					ProductNumber.setText("");
				}
				if(arg0.getKeyCode() == KeyEvent.VK_UP) {
					System.out.println("UP");
					BillType = false;
					FileOutputStream fout;
					try {
						Savenumber++;
						fout = new FileOutputStream("/Users/Sagar/Downloads/Invoice "+ Integer.toString(Savenumber) + CurrentInvoice.getCustomerName() + ".txt");
						ObjectOutputStream out=new ObjectOutputStream(fout);  
						out.writeObject(CurrentInvoice);  
						out.flush();
						out.close();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated cACZatch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}  
					
					CreatePDFInvoice();
					PrintInvoice();
					
				}
				if(arg0.getKeyCode() == KeyEvent.VK_DOWN) {
					ReadInitials();
					BillType = true;
					CreatePDFInvoice();
					PrintInvoice();
				}
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				
			}
			
		};
		
		HomeFrame.setFocusable(true);
	    
	}
	
	public void ReadInitials(){
		
		ProductInitialsList.clear();
		
		try{
			BufferedReader filein = new BufferedReader(new FileReader("C:/Users/Khurana Store/Desktop/Khurana Store/Docs/Savenumber.txt"));
			String sCurrentLine;
			sCurrentLine = filein.readLine();
			Savenumber = Integer.parseInt(sCurrentLine);
			filein.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		try{
			BufferedReader filein = new BufferedReader(new FileReader("C:/Users/Khurana Store/Desktop/Khurana Store/Docs/Initials.txt"));
			String sCurrentLine;
			sCurrentLine = filein.readLine();
			InvoiceNo = Integer.parseInt(sCurrentLine);
			while ((sCurrentLine = filein.readLine()) != null) 
			{	
				ProductInitialsList.add(SentenceCase(sCurrentLine));
			}
			filein.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
//		FileWriter fw;
//		try {
//			fw = new FileWriter("C:/Users/Sagar/Downloads/Initials.txt");
//			for(String s:ProductInitialsList){
//				fw.append(s + "\n");
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		Product_Initials = ProductInitialsList.toArray(new String[ProductInitialsList.size()]);
	}
	
	public void WriteInvoiceNo(){
		FileWriter fw;
		
		try{
			fw = new FileWriter("C:/Users/Khurana Store/Desktop/Khurana Store/Docs/Savenumber.txt");
			fw.append(((Integer)Savenumber).toString());
			fw.close();
		}catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			fw = new FileWriter("C:/Users/Khurana Store/Desktop/Khurana Store/Docs/Initials.txt");
			fw.append(((Integer)InvoiceNo).toString());
			fw.close();
		}catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void AddItems(){
		
		AllProducts.clear();
		try{
			BufferedReader filein = new BufferedReader(new FileReader("C:/Users/Khurana Store/Desktop/Khurana Store/Docs/Items.csv"));
			String sCurrentLine;
			sCurrentLine = filein.readLine();       // Skip Header
			while ((sCurrentLine = filein.readLine()) != null) 
			{	
				String s[]= sCurrentLine.split(",");
				Product p = new Product(s[0],Double.parseDouble(s[2]),Double.parseDouble(s[3]),Double.parseDouble(s[1]));
				AllProducts.add(p);
			}
			filein.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void homeScreen(){
		
		HomeFrame.setTitle("Billing Software");
		
		HomeFrame.getRootPane().setDefaultButton(InvoiceMenuButton);
		
		clearScreen();
		
		Container x = HomeFrame.getContentPane();
		x.setLayout(new GridBagLayout());
		c.weightx = 0.5;
	    c.gridx = 0;
	    c.gridy = 0;
	    c.ipadx = 50;
	    c.ipady = 25;
		x.add(InvoiceMenuButton,c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		c.ipadx = 0;
	    c.ipady = 0;
		x.add(Box.createRigidArea(new Dimension(30,30)),c);
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 2;
		c.ipadx = 50;
	    c.ipady = 20;
		x.add(ItemMenuButton,c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 3;
		c.ipadx = 0;
	    c.ipady = 0;
		x.add(Box.createRigidArea(new Dimension(30,30)),c);
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 4;
		c.ipadx = 50;
	    c.ipady = 20;
		x.add(OpenInvoiceButton,c);
		x.setBackground(Color.white);
		
		HomeFrame.setVisible(true);
		
	}
	
	public void clearScreen(){
		
		HomeFrame.getContentPane().removeAll();
		HomeFrame.getContentPane().revalidate();
		HomeFrame.getContentPane().repaint();
		
	}
	
	public void createInvoiceMenuScreen(){
		
		clearScreen();
		
		HomeFrame.setTitle("Invoice Menu");
		
		Container x = HomeFrame.getContentPane();
		x.setLayout(new GridBagLayout());
		c.weightx = 0.5;
	    c.gridx = 0;
	    c.gridy = 0;
	    c.ipadx = 50;
	    c.ipady = 25;
		x.add(CreateInvoiceButton,c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		c.ipadx = 0;
	    c.ipady = 0;
		x.add(Box.createRigidArea(new Dimension(30,30)),c);
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 2;
		c.ipadx = 50;
	    c.ipady = 20;
		x.add(ShowAllInvoicesButton,c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 3;
		c.ipadx = 0;
	    c.ipady = 0;
		x.add(Box.createRigidArea(new Dimension(30,30)),c);
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 4;
		c.ipadx = 0;
	    c.ipady = 0;
		x.add(BackToHomeButton,c);
		
		x.setBackground(Color.white);
		
	}
	
	public void createItemMenuScreen(){
		
		clearScreen();
		
		HomeFrame.setTitle("Item Menu");
		
		Container x = HomeFrame.getContentPane();
		x.setLayout(new GridBagLayout());
		c.weightx = 0.5;
	    c.gridx = 0;
	    c.gridy = 0;
	    c.ipadx = 25;
	    c.ipady = 10;
		x.add(SearchItemButton,c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		c.ipadx = 0;
	    c.ipady = 0;
		x.add(Box.createRigidArea(new Dimension(30,30)),c);
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 2;
		c.ipadx = 25;
	    c.ipady = 10;
		x.add(ViewAllItemsButton,c);
		x.setBackground(Color.white);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 3;
		c.ipadx = 0;
	    c.ipady = 0;
		x.add(Box.createRigidArea(new Dimension(30,30)),c);
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 4;
		c.ipadx = 25;
	    c.ipady = 10;
	    x.add(AddItemButton,c);
	    c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 5;
		c.ipadx = 0;
	    c.ipady = 0;
		x.add(Box.createRigidArea(new Dimension(30,30)),c);
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 6;
		c.ipadx = 25;
	    c.ipady = 10;
	    x.add(BackToHomeButton,c);
			
	}
	
	public void createSearchItemScreen(){
		
		clearScreen();
		
		HomeFrame.getRootPane().setDefaultButton(SearchItemResultButton);
		
		SearchItem.setText("");
		SearchItem.setFont(font1);
		Box SearchBox = Box.createHorizontalBox();
		SearchBox.add(SearchItem);
		SearchBox.add(Box.createRigidArea(new Dimension(10,0)));
		SearchBox.add(SearchItemResultButton);
		HomeFrame.getContentPane().setLayout(new BorderLayout());
		
		String[] columnNames = {"SNo","Product Name","MRP","Wholesale Price","Retail Price"};
		Object[][] data = new Object[0][5];
		
		SearchResultsModel = new DefaultTableModel(data,columnNames);
		
		SearchItemResults = new JTable(SearchResultsModel);
		
		SearchItemResults.setFont(font1);
		
		SearchItemResults.setRowSelectionAllowed(true);
//		SearchItemResults.setColumnSelectionAllowed(false);
//		SearchItemResults.setCellSelectionEnabled(false);
		
		ListSelectionModel cellSelectionModel = SearchItemResults.getSelectionModel();
	    cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	    cellSelectionModel.addListSelectionListener(n);
		
		JScrollPane scrollPane = new JScrollPane(SearchItemResults,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		int i;
		TableColumn column = null;
		
		for(i = 0; i < 5; i++){
			column = SearchItemResults.getColumnModel().getColumn(1);
			if(i == 1){
				column.setPreferredWidth(500);
			}
			else{
				column.setPreferredWidth(500);
			}
		}
		
		Box FinalBox = Box.createVerticalBox();
		FinalBox.add(SearchBox);
		FinalBox.add(scrollPane);
		
		scrollPane.setPreferredSize(new Dimension(1000,600));
		
		HomeFrame.getContentPane().setLayout(new BorderLayout());
		HomeFrame.getContentPane().add(FinalBox,BorderLayout.CENTER);
		HomeFrame.add(BackToItemMenuButton,BorderLayout.SOUTH);
		
	}

	public void createViewAllItemsScreen(){
		
		clearScreen();
//		createDemoItems();
		
		HomeFrame.getRootPane().setDefaultButton(BackToItemMenuButton);
		
		String[] columnNames = {"SNo","Product Name","MRP","Wholesale Price","Retail Price"};
		Object[][] data = new Object[0][5];
		
		ViewAllItemsModel = new DefaultTableModel(data,columnNames);
		
		Collections.sort(AllProducts, new Comparator<Product>() {
	        @Override
	        public int compare(final Product object1, final Product object2) {
	        return object1.getName().compareTo(object2.getName());
	        }
	    } );
		
		int i = 0;
		for(Product p : AllProducts){
			
			Object[] x = {Integer.toString(1 + i), p.getName(),Double.toString(p.getMRP()),Double.toString(p.getWholesaleRate()),Double.toString(p.getRetailRate())};
			ViewAllItemsModel.addRow(x);
			i++;
			
		}
		
		ViewAllItemsTable = new JTable(ViewAllItemsModel);
		
		ViewAllItemsTable.setRowSelectionAllowed(true);
//		ViewAllItemsTable.setColumnSelectionAllowed(false);
//		ViewAllItemsTable.setCellSelectionEnabled(false);
		
		ViewAllItemsTable.setFont(font1);
		
		ListSelectionModel cellSelectionModel = ViewAllItemsTable.getSelectionModel();
	    cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	    cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
	      public void valueChanged(ListSelectionEvent e) {

	        int[] selectedRow = ViewAllItemsTable.getSelectedRows();
	        	EditProduct = AllProducts.get(selectedRow[0]);
	        	createEditItemScreen();
	      }

	    });
		
		JScrollPane scrollPane = new JScrollPane(ViewAllItemsTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		TableColumn column = null;
		
			column = ViewAllItemsTable.getColumnModel().getColumn(1);
				column.setPreferredWidth(500);
		
//		ViewAllItemsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		scrollPane.setPreferredSize(new Dimension(1000,600));
		
		Box EndBox = Box.createHorizontalBox();
		EndBox.add(BackToItemMenuButton);
		EndBox.add(Box.createRigidArea(new Dimension(50,50)));
		EndBox.add(CreateItemPdf);
		
		HomeFrame.getContentPane().setLayout(new BorderLayout());
		HomeFrame.getContentPane().add(scrollPane,BorderLayout.CENTER);
		HomeFrame.getContentPane().add(EndBox,BorderLayout.SOUTH);
	}
	
	public void createInvoiceScreen(){
		
		clearScreen();

		HomeFrame.setFocusable(true);
		
		SearchItemForInvoice.requestFocus();
		
		HomeFrame.getRootPane().setDefaultButton(InvoiceCreationButton);
		
		Box CustomerNameBox = Box.createHorizontalBox();
		Box ContactBox = Box.createHorizontalBox();
		Box AddressBox = Box.createHorizontalBox();
		Box FinalBox = Box.createVerticalBox();
		CustomerNameField.setFocusable(true);
		CustomerNameField.requestFocus();
		CustomerNameField.setText("");
		ContactField.setText("");
		Address.setText("");
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		radioGroup.add(rbutton1 = new JRadioButton("WholeSale Bill"));
		rbutton1.setName("WholeSale Bill");
		rbutton1.setSelected(true);
		panel1.add(rbutton1, BorderLayout.PAGE_START);
		radioGroup.add(rbutton2 = new JRadioButton("Retail Bill"));
		rbutton2.setName("Retail Bill");
		panel2.add(rbutton2, BorderLayout.PAGE_START);
		rbutton1.setBackground(Color.white);
		rbutton2.setBackground(Color.white);
		JLabel CutomerNameLabel = new JLabel(" Customer Name : ");
		JLabel ContactLabel = new JLabel(" Contact : ");
		JLabel AddressLabel = new JLabel("Address : ");
		CustomerNameBox.add(CutomerNameLabel);
		CustomerNameBox.add(CustomerNameField);
		FinalBox.add(CustomerNameBox);
		FinalBox.add(Box.createRigidArea(new Dimension(50,20)));
		FinalBox.add(ContactBox);
		ContactBox.add(ContactLabel);
		ContactBox.add(ContactField);
		AddressBox.add(AddressLabel);
		AddressBox.add(Address);
		FinalBox.add(Box.createRigidArea(new Dimension(50,20)));
		FinalBox.add(AddressBox);
		FinalBox.add(Box.createRigidArea(new Dimension(50,20)));
		panel1.setBackground(Color.WHITE);
		panel2.setBackground(Color.WHITE);
		FinalBox.add(panel1);
		FinalBox.add(panel2);
		
		FinalBox.add(Box.createRigidArea(new Dimension(50,20)));
		FinalBox.add(InvoiceCreationButton);
		FinalBox.add(Box.createRigidArea(new Dimension(50,20)));
		FinalBox.add(BackToInvoiceMenuButton);
		
		HomeFrame.add(FinalBox);
			
	}
	
	public void createAddItemScreen(){
		
		clearScreen();
		
		AddItemHeaderLabel.setText("");
		
		HomeFrame.getRootPane().setDefaultButton(AddingItemButton);
		
		ProductName = new JTextField();
		MRP = new JTextField();
		WholesalePrice = new JTextField();
		RetailPrice = new JTextField();
		
		Box ProductNameBox = Box.createHorizontalBox();
		Box MRPBox = Box.createHorizontalBox();
		Box WholesalePriceBox = Box.createHorizontalBox();
		Box RetailPriceBox = Box.createHorizontalBox();
		Box FinalBox = Box.createVerticalBox();
		
		JLabel ProductNameLabel = new JLabel("Product Name : ");
		JLabel MRPLabel = new JLabel("MRP : ");
		JLabel WholesalePriceLabel = new JLabel("WholeSale Price : ");
		JLabel RetailPriceLabel = new JLabel("Retail Price : ");
		
		ProductNameBox.add(ProductNameLabel);
		ProductNameBox.add(ProductName);
		
		MRPBox.add(MRPLabel);
		MRPBox.add(MRP);
		
		WholesalePriceBox.add(WholesalePriceLabel);
		WholesalePriceBox.add(WholesalePrice);
		
		RetailPriceBox.add(RetailPriceLabel);
		RetailPriceBox.add(RetailPrice);
		
		FinalBox.add(AddItemHeaderLabel);
		FinalBox.add(Box.createRigidArea(new Dimension(50,20)));
		FinalBox.add(ProductNameBox);
		FinalBox.add(Box.createRigidArea(new Dimension(50,20)));
		FinalBox.add(MRPBox);
		FinalBox.add(Box.createRigidArea(new Dimension(50,20)));
		FinalBox.add(WholesalePriceBox);
		FinalBox.add(Box.createRigidArea(new Dimension(50,20)));
		FinalBox.add(RetailPriceBox);
		FinalBox.add(Box.createRigidArea(new Dimension(50,20)));
		FinalBox.add(AddingItemButton);
		FinalBox.add(Box.createRigidArea(new Dimension(50,20)));
		FinalBox.add(BackToItemMenuButton);
		
		HomeFrame.add(FinalBox);
		
	}
	
	public void showAllInvoicesScreen(){
		
		clearScreen();
		
	}
	
	public void createEditItemScreen(){
		
		clearScreen();

		HomeFrame.getRootPane().setDefaultButton(BackToViewAllItemsButton);
		
		JLabel Product_Name = new JLabel("Product Name : " + EditProduct.getName());
		Product_Name.setFont(new Font("Arial", Font.PLAIN, 20));
		JLabel MRP = new JLabel("MRP : " + Double.toString(EditProduct.getMRP()));
		MRP.setFont(new Font("Arial", Font.PLAIN, 20));
		JLabel WholesalePrice = new JLabel("Wholesale Price : " + Double.toString(EditProduct.getWholesaleRate()));
		WholesalePrice.setFont(new Font("Arial", Font.PLAIN, 20));
		JLabel RetailPrice = new JLabel("Retail Price : " + Double.toString(EditProduct.getRetailRate()));
		RetailPrice.setFont(new Font("Arial", Font.PLAIN, 20));
		
		Box FinalBox = Box.createVerticalBox();
		Box NameBox = Box.createHorizontalBox();
		Box MRPBox = Box.createHorizontalBox();
		Box WholBox = Box.createHorizontalBox();
		Box RetailBox = Box.createHorizontalBox();
		
		NameBox.add(Product_Name);
		NameBox.add(Box.createRigidArea(new Dimension(35,20)));
		NameBox.add(EditNameButton);
		MRPBox.add(MRP);
		MRPBox.add(Box.createRigidArea(new Dimension(170,20)));
		MRPBox.add(EditMRPButton);
		WholBox.add(WholesalePrice);
		WholBox.add(Box.createRigidArea(new Dimension(113,20)));
		WholBox.add(EditWholButton);
		RetailBox.add(RetailPrice);
		RetailBox.add(Box.createRigidArea(new Dimension(117,20)));
		RetailBox.add(EditRetButton);
		FinalBox.add(Box.createRigidArea(new Dimension(75,100)));
		FinalBox.add(NameBox);
		FinalBox.add(Box.createRigidArea(new Dimension(50,50)));
		FinalBox.add(MRPBox);
		FinalBox.add(Box.createRigidArea(new Dimension(50,50)));
		FinalBox.add(RetailBox);
		FinalBox.add(Box.createRigidArea(new Dimension(50,50)));
		FinalBox.add(WholBox);
		FinalBox.add(Box.createRigidArea(new Dimension(50,50)));
		FinalBox.add(DeleteItemButton);
		FinalBox.add(Box.createRigidArea(new Dimension(50,50)));
		FinalBox.add(BackToViewAllItemsButton);
		HomeFrame.getContentPane().setLayout(new BorderLayout());
		HomeFrame.add(FinalBox,BorderLayout.CENTER);
		
	}
	
	public void createInvoiceCreationScreen(){
		
		clearScreen();
		
		AddItems();
		
		HomeFrame.setFocusable(true);
		
		SearchItemForInvoice.requestFocus();
		
		HomeFrame.addKeyListener(Searchkl);
		
		HomeFrame.getRootPane().setDefaultButton(SearchItemInvoiceButton);
		
		
		
		Box SearchFieldBox = Box.createHorizontalBox();
		Box SearchBox = Box.createVerticalBox();
		Box CustomerDetailsBox = Box.createVerticalBox();
		Box TableBox = Box.createHorizontalBox();
		
		SearchFieldBox.add(Box.createRigidArea(new Dimension(50,50)));
//		SearchFieldBox.setFocusable(true);
//		SearchFieldBox.addKeyListener(Searchkl);
		SearchItemForInvoice.setText("");
		SearchItemForInvoice.addKeyListener(Searchkl);
		SearchItemForInvoice.setFont(font1);
		SearchFieldBox.add(ProductNumber);
		ProductNumber.addKeyListener(Searchkl);
		SearchFieldBox.add(Box.createRigidArea(new Dimension(50,50)));
		SearchFieldBox.add(SearchItemForInvoice);
		SearchFieldBox.add(Box.createRigidArea(new Dimension(50,50)));
		SearchFieldBox.add(SearchItemInvoiceButton);
		SearchItemInvoiceButton.addKeyListener(Searchkl);
		
		TableColumn column = null;
		
		SearchResultsTableInvoice.setFont(font1);
		SearchResultsTableInvoice.addKeyListener(Searchkl);
		SearchResultsTableInvoice.setRowHeight(19);
		
		column = SearchResultsTableInvoice.getColumnModel().getColumn(0);
		column.setPreferredWidth(15);
		column = SearchResultsTableInvoice.getColumnModel().getColumn(1);
		column.setPreferredWidth(300);
		column = SearchResultsTableInvoice.getColumnModel().getColumn(2);
		column.setPreferredWidth(40);
		column = SearchResultsTableInvoice.getColumnModel().getColumn(3);
		column.setPreferredWidth(40);
		column = SearchResultsTableInvoice.getColumnModel().getColumn(4);
		column.setPreferredWidth(40);
	    
		Box DetailsBox = Box.createHorizontalBox();
		
	    SearchBox.add(SearchFieldBox);
	    scrollPane.setPreferredSize(new Dimension(1500,1500));
	    JLabel Customer_Name = new JLabel("Custome Name : " + CurrentInvoice.getCustomerName());
	    JLabel Contact_Details = new JLabel("Contact : " + CurrentInvoice.getContact());
	    JLabel Customer_Address = new JLabel("Address : " + CurrentInvoice.getAddress());
	    DetailsBox.add(Customer_Name);
	    DetailsBox.add(Box.createRigidArea(new Dimension(50,20)));
	    DetailsBox.add(Contact_Details);
	    DetailsBox.add(Box.createRigidArea(new Dimension(50,20)));
	    DetailsBox.add(Customer_Address);
	    DetailsBox.add(Box.createRigidArea(new Dimension(50,20)));
	    
	    JLabel PaidAmountLabel = new JLabel("Amount Paid");
		
	    Box RemarksBox = Box.createHorizontalBox();
		RemarksBox.add(Remarks);
		RemarksBox.add(Box.createRigidArea(new Dimension(50,20)));
		RemarksBox.add(RemarksField);
		
		RemarksField.setFont(font1);
		RemarksField.addKeyListener(Searchkl);
		
		Box PaidAmountBox = Box.createHorizontalBox();
		PaidAmountBox.add(PaidAmountLabel);
		PaidAmountBox.add(Box.createRigidArea(new Dimension(10,10)));
		PaidAmountField.setFont(font1);
		PaidAmountField.addKeyListener(Searchkl);
		PaidAmountBox.add(PaidAmountField);
	    
		CustomerDetailsBox.add(DetailsBox);
		TableBox.add(scrollPane);
		TableBox.add(Box.createRigidArea(new Dimension(10,10)));
		TableBox.add(scrollPane1);
		CustomerDetailsBox.add(Box.createRigidArea(new Dimension(10,10)));
		CustomerDetailsBox.add(TableBox);
		CustomerDetailsBox.add(Box.createRigidArea(new Dimension(10,10)));
		CustomerDetailsBox.add(InvoiceTotalLabel);
		CustomerDetailsBox.add(Box.createRigidArea(new Dimension(10,10)));
		CustomerDetailsBox.add(PaidAmountBox);
		CustomerDetailsBox.add(Box.createRigidArea(new Dimension(10,10)));
		CustomerDetailsBox.add(RemarksBox);
		
		Box EndBox = Box.createHorizontalBox();
		EndBox.add(BackToInvoiceCreation);
		BackToInvoiceCreation.setPreferredSize(new Dimension(150,150));
		BackToInvoiceCreation.setMnemonic(KeyEvent.VK_A);
		BackToInvoiceCreation.addKeyListener(Searchkl);
		EndBox.add(Box.createRigidArea(new Dimension(50,20)));
		EndBox.add(CreateInvoiceComplete);
		CreateInvoiceComplete.setPreferredSize(new Dimension(150,150));
		CreateInvoiceComplete.setMnemonic(KeyEvent.VK_2);
		CreateInvoiceComplete.addKeyListener(Searchkl);
		EndBox.add(Box.createRigidArea(new Dimension(50,20)));
		PakkaBill.setPreferredSize(new Dimension(150,150));
		EndBox.add(PakkaBill);
		PakkaBill.addKeyListener(Searchkl);
		PakkaBill.setMnemonic(KeyEvent.VK_3);
		CustomerDetailsBox.add(EndBox);
		
		InvoiceItemsTable.setRowHeight(19);
		
		InvoiceItemsTable.setFont(font1);
		InvoiceItemsTable.addKeyListener(Searchkl);
		
		column = InvoiceItemsTable.getColumnModel().getColumn(0);
		column.setPreferredWidth(15);
		column = InvoiceItemsTable.getColumnModel().getColumn(1);
		column.setPreferredWidth(300);
		column = InvoiceItemsTable.getColumnModel().getColumn(2);
		column.setPreferredWidth(40);
		column = InvoiceItemsTable.getColumnModel().getColumn(3);
		column.setPreferredWidth(40);
		column = InvoiceItemsTable.getColumnModel().getColumn(4);
		column.setPreferredWidth(40);
		
		scrollPane1.setPreferredSize(new Dimension(1500,1500));
		
		int i = 0;
    	for( i = (InvoiceItemsModel.getRowCount() - 1); i >= 0 ; i--){
    		InvoiceItemsModel.removeRow(i);
    	}
    	
    	CurrentInvoiceItemList.clear();
    	int k = 0;
    	for(Entry<Product,Integer> entry : CurrentInvoice.getListOfProducts().entrySet()){
    	
    		CurrentInvoiceItemList.add(entry.getKey());
    		
        	if(CurrentInvoice.getType() == 'W'){
        		Object[] Whol = {Integer.toString( 1 + k ),entry.getKey().getName(),entry.getValue(),entry.getKey().getWholesaleRate(),(entry.getKey().getWholesaleRate()*entry.getValue())};
    			InvoiceItemsModel.addRow(Whol);
        	}else{
        		Object[] Ret = {Integer.toString( 1 + k ),entry.getKey().getName(),entry.getValue(),entry.getKey().getRetailRate(),(entry.getKey().getRetailRate()*entry.getValue())};
    			InvoiceItemsModel.addRow(Ret);
        	}
    		k++;
    	}
    	InvoiceTotalLabel.setText("Invoice Total : " + CurrentInvoice.getTotal());
    	
    	
    	
    	int j = 0;
    	for(j = (SearchResultTableModel.getRowCount() - 1); j >= 0;  j--){
			SearchResultTableModel.removeRow(j);
		}
    	
    	SearchItemInvoiceList.clear();
    	
    	SearchItemForInvoice.getDocument().addDocumentListener(new DocumentListener() {
			
			
			public void Do() {
				AutoUpdateSuggestions("");
			}
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				Do();
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				Do();
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				Do();
			}
		});
		
		HomeFrame.getContentPane().setLayout(new BorderLayout());
		Box FinalBox = Box.createVerticalBox();
		FinalBox.add(SearchBox);
		FinalBox.add(CustomerDetailsBox);
		HomeFrame.getContentPane().add(FinalBox, BorderLayout.CENTER);
		
		

	}
	
	public void AutoUpdateSuggestions( String x1) {
		
		
		String Item;
		if(x1.equals("") || x1.equals(" ")) {
			Item = SearchItemForInvoice.getText().toLowerCase(); 
		}
		else {
			Item = x1;
		}
		AddItems();
		
		ListSelectionModel cellSelectionModel = SearchResultsTableInvoice.getSelectionModel();
		cellSelectionModel.removeListSelectionListener(listenetSearchResults);
		int j = 0;
		SearchItemInvoiceList.clear();
		SearchResultsTableInvoice.clearSelection();
		String[] s = Item.split(" ");
		ArrayList<String> search = new ArrayList<String>(Arrays.asList(s));
		ArrayList<String> Pro;
		int i = 0, l = 0;
		SearchItemInvoiceList.clear();
		int flag = 0;
//		for(Product p : AllProducts){
//			Pro = new ArrayList<String>(Arrays.asList(p.getName().toLowerCase().split(" ")));
//			flag = 0;
//			for(i = 0; i < search.size(); i++){
//				for(l = 0; l < Pro.size(); l++){
//					if(Pro.get(l).startsWith(search.get(i))){
//						
//					}
//				}
//			}
//		}
		for(Product p : AllProducts){
			if(p.getName().toLowerCase().startsWith(Item)){
				if(!SearchItemInvoiceList.contains(p))
					SearchItemInvoiceList.add(p);
			}
		}
		for(Product p : AllProducts){
			Pro = new ArrayList<String>(Arrays.asList(p.getName().toLowerCase().split(" ")));
			for(String s1 : search){
				for(l = 0; l < Pro.size(); l++){
					if(Pro.get(l).equals(s1)){
						if(!SearchItemInvoiceList.contains(p))
							SearchItemInvoiceList.add(p);
					}
				}
			}
		}
		for(Product p : AllProducts){
			Pro = new ArrayList<String>(Arrays.asList(p.getName().toLowerCase().split(" ")));
			for(String s1 : search){
				flag = 0;
				for(l = 0; l < Pro.size(); l++){
					if(!Pro.get(l).startsWith(s1)){
						flag = 1;
					}
				}
				if(flag == 0){
					if(!SearchItemInvoiceList.contains(p))
						SearchItemInvoiceList.add(p);
				}
			}
		}
		for(Product p1 : AllProducts){
			flag = 0;
			for(String s1 : search){
				if(!p1.getName().toLowerCase().contains(s1)){
					flag = 1;
				}
			}
			if(flag == 0){
				if(!SearchItemInvoiceList.contains(p1))
					SearchItemInvoiceList.add(p1);
			}
		}
		for(Product p : AllProducts){
			Pro = new ArrayList<String>(Arrays.asList(p.getName().split(" ")));
			flag = 0;
			for(l = 0; l < Pro.size(); l++){
				for(i = 0; i < search.size(); i++){
					if(Pro.get(l).toLowerCase().startsWith(search.get(i).toLowerCase())){
						flag = 1;
					}
				}
			}
			if(flag == 1){
				if(!SearchItemInvoiceList.contains(p))
					SearchItemInvoiceList.add(p);
			}
		}
		for(j = (SearchResultTableModel.getRowCount() - 1); j >= 0;  j--){
			SearchResultTableModel.removeRow(j);
		}
		int k = 0;
		for(Product p2 : SearchItemInvoiceList){
				Object[] x = {Integer.toString(1 + k), p2.getName(),Double.toString(p2.getMRP()),Double.toString(p2.getWholesaleRate()),Double.toString(p2.getRetailRate())};
				SearchResultTableModel.addRow(x);
				k++;
		}
		cellSelectionModel.addListSelectionListener(listenetSearchResults);
		//createInvoiceCreationScreen();

	}
	
	
	
	public class ItemMenuAction implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			createItemMenuScreen();
		}
		
	}
	
	public class InvoiceMenuAction implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			createInvoiceScreen();
		}
		
	}
	
	public class SearchItemAction implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			AddItems();
			createSearchItemScreen();
		}
		
	}
	
	public class CreateInvoiceAction implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			AddItems();
			createInvoiceScreen();
		}
		
	}
	
	public class ShowAllInvoicesAction implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			showAllInvoicesScreen();
		}
		
	}
	
	public class ViewAllItemsAction implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			AddItems();
			createViewAllItemsScreen();
		}
		
	}
	
	public class AddItemAction implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			createAddItemScreen();
		}
		
	}
	
	public class BackToHomeAction implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			clearScreen();
			HomeFrame.setVisible(false);
			View_Controller view = new View_Controller();
			view.homeScreen();
		}
		
	}
	
	public class OpenInvoiceAction implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			String s = (String)JOptionPane.showInputDialog(HomeFrame,"Enter Save Number","Invoice Number",JOptionPane.PLAIN_MESSAGE,null,null,null);
			try {
				ObjectInputStream in=new ObjectInputStream(new FileInputStream("C:/Users/Khurana Store/Desktop/Khurana Store/Bills/Invoice "+ s + " .txt"));
				CurrentInvoice = (Invoice)in.readObject();
				in.close();
				//System.out.println(CurrentInvoice.getProducts());
				createInvoiceCreationScreen();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  
			
		}
		
	}
	
	public class BackToItemMenuAction implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			
			createItemMenuScreen();
		}
		
	}

	public class BackToInvoiceMenuAction implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			//HomeFrame.removeKeyListener(Searchkl);
			createInvoiceMenuScreen();
		}
		
	}
	
	public class CompleteCurrentInvoiceAction implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			
			BillType = false;
			FileOutputStream fout;
			try {
				Savenumber++;
				WriteInvoiceNo();
				fout = new FileOutputStream("C:/Users/Khurana Store/Desktop/Khurana Store/Bills/Invoice "+ Integer.toString(Savenumber) + CurrentInvoice.getCustomerName() + ".txt");
				ObjectOutputStream out=new ObjectOutputStream(fout);  
				out.writeObject(CurrentInvoice);  
				out.flush();
				out.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated cACZatch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  
			
			CreatePDFInvoice();
			PrintInvoice();
			
			
////			System.out.println("/Users/Sagar/Desktop/Invoice " + CurrentInvoice.getCustomerName() + CurrentInvoice.getTime().getDate() + Integer.toString(CurrentInvoice.getTime().getMonth() + 1) + "_" + Integer.toString(CurrentInvoice.getTime().getHours())  + Integer.toString(CurrentInvoice.getTime().getMinutes()) + ".txt");
//			File file = new File("/Users/Sagar/Desktop/Invoice "+ CurrentInvoice.getCustomerName() + " " + CurrentInvoice.getTime().getDate() + Integer.toString(CurrentInvoice.getTime().getMonth() + 1) + "_" + Integer.toString(CurrentInvoice.getTime().getHours())  + Integer.toString(CurrentInvoice.getTime().getMinutes()) + ".txt");	
//			try {
//				file.createNewFile();
//				String add = "Address", cust = "Customer Name", cont = "Contact", est = "Rough Estimate";
//				PrintWriter Write = new PrintWriter(new BufferedWriter(new FileWriter(file)));
//				Write.printf("%50s\n",est);
//				Write.println("");
//				Write.println("Date : " + CurrentInvoice.getTime().toString());
//				Write.printf("%13s : %-30s    %7s : %-15s\n",cust,CurrentInvoice.getCustomerName(),cont,CurrentInvoice.getContact());
//				Write.printf("%7s : %-30s\n",add,CurrentInvoice.getAddress());
//				Write.println("");
//				String SNo = "Sno.", Quantity = "Quantity" ,ProductName = "Product Name" , Rate = "Rate",SubTotal = "Price", Remarks = "Remarks", MRP = "MRP"; 
//				Write.printf("%-5s | %-10s | %-50s | %-6s | %-6s | %-6s\n",SNo,Quantity,ProductName,MRP,Rate,SubTotal);
//				Map<Product,Integer> Products = CurrentInvoice.getListOfProducts();
//				int i = 0;
//				for(Entry<Product, Integer> entry : Products.entrySet()){
//					if(CurrentInvoice.getType() == 'W'){
//						Write.printf("%-5d | %-10d | %-50s | %-6.2f | %-6.2f | %-6.2f\n",(i+1),entry.getValue(),entry.getKey().getName(),entry.getKey().getMRP(),entry.getKey().getWholesaleRate(),(entry.getKey().getWholesaleRate()* entry.getValue()));
//					}else if(CurrentInvoice.getType() == 'R'){
//						Write.printf("%-5d | %-10d | %-50s | %-6.2f | %-6.2f | %-6.2f\n",(i+1),entry.getValue(),entry.getKey().getName(),entry.getKey().getMRP(),entry.getKey().getRetailRate(),(entry.getKey().getRetailRate()* entry.getValue()));
//					}
//					i++;
//				}
//				Write.println("");
//				Write.printf("%55s : %-10.2f","Total",CurrentInvoice.getTotal());
//				Write.println("");
//				Write.printf("%15s : %-50s" ,Remarks,CurrentInvoice.getRemarks());
//				Write.close();
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			
//			String outputFile = ("/Users/Sagar/Desktop/Invoice "+ CurrentInvoice.getCustomerName() + " " + CurrentInvoice.getTime().getDate() + Integer.toString(CurrentInvoice.getTime().getMonth() + 1) + "_" + Integer.toString(CurrentInvoice.getTime().getHours())  + Integer.toString(CurrentInvoice.getTime().getMinutes()) + ".pdf");
//			Document document = new Document(PageSize.A4, 50, 50, 50, 50);
//			
//			try {
//				@SuppressWarnings("unused")
//				PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(outputFile));
//				document.setMargins(15f,15f,15f,15f);
//				document.open();
//				
//				BufferedReader br = new BufferedReader(new FileReader(file));
//				Paragraph p;
//				String sCurrentLine;
//				while ((sCurrentLine = br.readLine()) != null) 
//				{	
//					p = new Paragraph(sCurrentLine + "\n",FontFactory.getFont(FontFactory.TIMES,14));
//					document.add(p);
//				}
//				br.close();
//				document.close();
//			} catch (FileNotFoundException | DocumentException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}finally{
//				clearScreen();
//				homeScreen();
//			}
			
			
			
		}
	}
	
	public class PakkaBillAction implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			ReadInitials();
			BillType = true;
			CreatePDFInvoice();
			PrintInvoice();
		}
		
	}
	
	public void PrintInvoice(){
		
		try {
			PDDocument document = PDDocument.load(new File(outputFile));
			PrinterJob job = PrinterJob.getPrinterJob();
			job.setPageable(new PDFPageable(document));
			if(job.printDialog()) {
			       job.print();        
			 }        
		} catch (IOException | PrinterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public void CreatePDFInvoice(){
		
		String remarks = RemarksField.getText();
		CurrentInvoice.setRemarks(remarks);
		if(PaidAmountField.getText() == null || PaidAmountField.getText().equals("")){
			CurrentInvoice.setPaidAmount(0);
			CurrentInvoice.setNetAmount(CurrentInvoice.getTotal() - CurrentInvoice.getPaidAmount());
		}
		else{
			double amt = Double.valueOf(PaidAmountField.getText());
			CurrentInvoice.setPaidAmount(amt);
			CurrentInvoice.setNetAmount(CurrentInvoice.getTotal() - CurrentInvoice.getPaidAmount());
		}
		if(BillType == true){
			outputFile = ("C:/Users/Khurana Store/Desktop/Khurana Store/Bills/Invoice " + CurrentInvoice.getCustomerName() + " " + InvoiceNo + ".pdf");
		}
		else{
			outputFile = ("C:/Users/Khurana Store/Desktop/Khurana Store/Bills/Invoice "+ CurrentInvoice.getCustomerName() + " " + CurrentInvoice.getTimeStamp().getDate() + Integer.toString(CurrentInvoice.getTimeStamp().getMonth() + 1) + "_" + Integer.toString(CurrentInvoice.getTimeStamp().getHours())  + Integer.toString(CurrentInvoice.getTimeStamp().getMinutes()) + ".pdf");
			
		}
		Document document = new Document(PageSize.A5);
			try {
			@SuppressWarnings("unused")
			PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(outputFile));
//			writer.setPageEvent(new PDFPageEventAddInvoiceName());
			document.setMargins(20f,20f,10f,10f);
			document.open();
			PdfPCell cell;
			PdfPTable table;
			
			if(BillType == true){
				String s = (String)JOptionPane.showInputDialog(HomeFrame,"Enter Date and Time","Date & Time",JOptionPane.PLAIN_MESSAGE,null,null,null);
				try {
					Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(s);
					CurrentInvoice.setTime(date1);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Paragraph p = new Paragraph("Khurana Store",FontFactory.getFont("Arial",15,Font.BOLD));
				p.setAlignment(Element.ALIGN_CENTER);
				document.add(p);
				p = new Paragraph("2/151,Subhash Nagar ND-110027 | +91-9654595674\nGSTIN - 07AEFPP9789Q1Z6",FontFactory.getFont("Arial",11,Font.BOLD));
				p.setAlignment(Element.ALIGN_CENTER);
				document.add(p);
//				p = new Paragraph("Invoice No- KHS/17-18/" + df1.format(InvoiceNo));
//				p.setAlignment(Element.ALIGN_LEFT);
//				document.add(p);
				table = new PdfPTable(3);
				table.setSpacingBefore(5);
				cell = new PdfPCell(new Phrase("Bill of Supply",FontFactory.getFont("Arial",13,Font.BOLD)));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(3);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase("Invoice No - KS/18-19/" + df1.format(InvoiceNo) + "\nDate of Issue - " + CurrentInvoice.getDate() ,FontFactory.getFont("Arial",11)));
				InvoiceNo++;
				
				cell.setColspan(2);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase("State of supply - Delhi\nState Code - 07",FontFactory.getFont("Arial",11)));
				table.addCell(cell);
				table.setHorizontalAlignment(Element.ALIGN_LEFT);
		        table.setWidthPercentage(100);
				cell = new PdfPCell(new Phrase("Billed To/Details of Receiver :",FontFactory.getFont("Arial",11,Font.BOLD)));
				cell.setColspan(3);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase("Name - " + CurrentInvoice.getCustomerName() + "\nAddress - " + CurrentInvoice.getAddress() +"\nContact - " + CurrentInvoice.getContact(),FontFactory.getFont("Arial",11)));
				cell.setColspan(2);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase("State of supply - Delhi\nState Code - 07\nGSTIN -",FontFactory.getFont("Arial",11)));
				table.addCell(cell);
				table.setHorizontalAlignment(Element.ALIGN_LEFT);
		        table.setWidthPercentage(100);
				document.add(table);
				
//				table = new PdfPTable(2);
//				cell = new PdfPCell(new Paragraph("Khurana Store",FontFactory.getFont("Arial",11)));
//				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//				cell.setColspan(2);
//				table.addCell(cell);
//				table.addCell("Tin");
//				table.addCell("");
//				cell = new PdfPCell(new Phrase("Address"));
//				cell.setColspan(1);
//				table.addCell(cell);
//				cell = new PdfPCell(new Phrase("2/151 Subhash Nagar New Delhi - 110027"));
//				cell.setColspan(1);
//				table.addCell(cell);
//				cell = new PdfPCell(new Phrase("Telphone No."));
//				cell.setColspan(1);
//				table.addCell(cell);
//				cell = new PdfPCell(new Phrase("011 - 45644903"));
//				cell.setColspan(1);
//				table.addCell(cell);
//				cell = new PdfPCell(new Phrase("Email "));
//				cell.setColspan(1);
//				table.addCell(cell);
//				cell = new PdfPCell(new Phrase("suraj.khuranastoresn@gmail.com"));
//				cell.setColspan(1);
//				table.addCell(cell);
//				document.add(table);
			}
			else{
				Paragraph p = new Paragraph("Rough Estimate",FontFactory.getFont("Arial",14,Font.BOLD));
				p.setAlignment(Element.ALIGN_CENTER);
				document.add(p);
				
				table = new PdfPTable(3);
				cell = new PdfPCell(new Paragraph("Name",FontFactory.getFont("Arial",11)));
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph(CurrentInvoice.getCustomerName(),FontFactory.getFont("Arial",11)));
				cell.setColspan(2);
				table.addCell(cell);
				table.setSpacingBefore(5);
				
				cell = new PdfPCell(new Paragraph("Date & Time",FontFactory.getFont("Arial",11)));
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(CurrentInvoice.getDate(),FontFactory.getFont("Arial",11)));
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(CurrentInvoice.getTime(),FontFactory.getFont("Arial",11)));
				table.addCell(cell);
				
//				cell = new PdfPCell(new Phrase("Contact",FontFactory.getFont("Arial",11)));
//				table.addCell(cell);
//				cell = new PdfPCell(new Phrase(CurrentInvoice.getContact(),FontFactory.getFont("Arial",11)));
//				cell.setColspan(2);
//				table.addCell(cell);
//				
//				cell = new PdfPCell(new Phrase("Address",FontFactory.getFont("Arial",11)));
//				table.addCell(cell);
//				cell = new PdfPCell(new Phrase(CurrentInvoice.getAddress(),FontFactory.getFont("Arial",11)));
//				cell.setColspan(2);
//				table.addCell(cell);
				table.setHorizontalAlignment(Element.ALIGN_LEFT);
				document.add(table);
			}
			
			
			WriteInvoiceNo();
			table = new PdfPTable(17);
			table.setSpacingBefore(5);
	        table.setSpacingAfter(7);
	        table.setHorizontalAlignment(Element.ALIGN_LEFT);
	        table.setWidthPercentage(100);
	        
//	        cell = new PdfPCell(new Phrase("Sno.",FontFactory.getFont("Arial",11)));
//	        cell.setColspan(2);
//	        table.addCell(cell);
			cell = new PdfPCell(new Phrase("Qty",FontFactory.getFont("Arial",11)));
	        cell.setColspan(1);
	        table.addCell(cell);
	        cell = new PdfPCell(new Phrase("Description of Goods",FontFactory.getFont("Arial",11)));
	        cell.setColspan(9);
	        table.addCell(cell);
	        cell = new PdfPCell(new Phrase("MRP",FontFactory.getFont("Arial",11)));
	        cell.setColspan(2);
	        table.addCell(cell);
	        cell = new PdfPCell(new Phrase("Rate",FontFactory.getFont("Arial",11)));
	        cell.setColspan(2);
	        table.addCell(cell);
	        cell = new PdfPCell(new Phrase("Price",FontFactory.getFont("Arial",11)));
	        cell.setColspan(3);
	        table.addCell(cell);
	        
			Map<Product,Integer> Products = CurrentInvoice.getListOfProducts();
			int i = 0;
			for(Entry<Product, Integer> entry : Products.entrySet()){
				if(CurrentInvoice.getType() == 'W'){
					
//					cell = new PdfPCell(new Phrase(Integer.toString(i+1),FontFactory.getFont("Arial",11)));
//			        cell.setColspan(2);
//			        table.addCell(cell);
					cell = new PdfPCell(new Phrase(Integer.toString(entry.getValue()),FontFactory.getFont("Arial",11)));
			        cell.setColspan(1);
			        table.addCell(cell);
			        String prodname = entry.getKey().getName();
			        String s = prodname.split(" ")[0];
			        if(s.equals(s.toUpperCase())) {
			        	cell = new PdfPCell(new Phrase(entry.getKey().getName(),FontFactory.getFont("Arial",11,Font.BOLD)));
//			        	System.out.println(s.toUpperCase());
//			        	System.out.println(s);
			        }
			        else {
			        cell = new PdfPCell(new Phrase(entry.getKey().getName(),FontFactory.getFont("Arial",11)));
			        }
			        cell.setColspan(9);
			        table.addCell(cell);
			        cell = new PdfPCell(new Phrase(df.format(entry.getKey().getMRP()),FontFactory.getFont("Arial",11)));
			        cell.setColspan(2);
			        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			        table.addCell(cell);
			        cell = new PdfPCell(new Phrase(df.format(entry.getKey().getWholesaleRate()),FontFactory.getFont("Arial",11)));
			        cell.setColspan(2);
			        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			        table.addCell(cell);
			        cell = new PdfPCell(new Phrase(df.format(entry.getKey().getWholesaleRate()* entry.getValue()),FontFactory.getFont("Arial",11)));
			        cell.setColspan(3);
			        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			        table.addCell(cell);
			        
				}else if(CurrentInvoice.getType() == 'R'){
					
//					cell = new PdfPCell(new Phrase(Integer.toString(i+1),FontFactory.getFont("Arial",11)));
//			        cell.setColspan(2);
//			        table.addCell(cell);
					cell = new PdfPCell(new Phrase(Integer.toString(entry.getValue()),FontFactory.getFont("Arial",11)));
			        cell.setColspan(1);
			        table.addCell(cell);
			        cell = new PdfPCell(new Phrase(entry.getKey().getName(),FontFactory.getFont("Arial",11)));
			        cell.setColspan(9);
			        table.addCell(cell);
			        cell = new PdfPCell(new Phrase(df.format(entry.getKey().getMRP()),FontFactory.getFont("Arial",11)));
			        cell.setColspan(2);
			        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			        table.addCell(cell);
			        cell = new PdfPCell(new Phrase(df.format(entry.getKey().getRetailRate()),FontFactory.getFont("Arial",11)));
			        cell.setColspan(2);
			        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			        table.addCell(cell);
			        cell = new PdfPCell(new Phrase(df.format(entry.getKey().getRetailRate()* entry.getValue()),FontFactory.getFont("Arial",11)));
			        cell.setColspan(3);
			        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			        table.addCell(cell);
					
				}
				i++;
			}
			
//			cell = new PdfPCell(new Phrase("Grand Total",FontFactory.getFont("Arial",11)));
//			cell.setColspan(14);
//			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			table.addCell(cell);
//			cell = new PdfPCell(new Phrase(df.format(CurrentInvoice.getTotal()),FontFactory.getFont("Arial",11)));
//	        cell.setColspan(3);
//	        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//	        table.addCell(cell);
			
	        if(CurrentInvoice.getPaidAmount() > 1.0) {
	        cell = new PdfPCell(new Phrase("Advance",FontFactory.getFont("Arial",11)));
			cell.setColspan(14);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase(df.format(CurrentInvoice.getPaidAmount()),FontFactory.getFont("Arial",11)));
	        cell.setColspan(3);
	        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        table.addCell(cell);
	        
	        }
	        if(CurrentInvoice.getRoundOff() > 0){
	        	cell = new PdfPCell(new Phrase("Round Off",FontFactory.getFont("Arial",11)));
	        	cell.setColspan(14);
	        	cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        	table.addCell(cell);
	        	cell = new PdfPCell(new Phrase("+ " + df.format(CurrentInvoice.getRoundOff()),FontFactory.getFont("Arial",11)));
	        	cell.setColspan(3);
	        	cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        	table.addCell(cell);
	        }else if(CurrentInvoice.getRoundOff() < 0){
	        	cell = new PdfPCell(new Phrase("Round Off",FontFactory.getFont("Arial",11)));
	        	cell.setColspan(14);
	        	cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        	table.addCell(cell);
	        	cell = new PdfPCell(new Phrase("- " + df.format(-1 * (CurrentInvoice.getRoundOff())),FontFactory.getFont("Arial",11)));
	        	cell.setColspan(3);
	        	cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        	table.addCell(cell);
	        }
			
			cell = new PdfPCell(new Phrase("Net Amount",FontFactory.getFont("Arial",12,Font.BOLD)));
			cell.setColspan(14);
			cell.setRowspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase(df.format(CurrentInvoice.getNetAmount()),FontFactory.getFont("Arial",12,Font.BOLD)));
	        cell.setColspan(3);
	        cell.setRowspan(2);
	        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        table.addCell(cell);
			
			document.add(table);
			Phrase noitems = new Phrase("Total Items",FontFactory.getFont("Arial",11,Font.BOLD));
			table = new PdfPTable(5);
			cell = new PdfPCell(noitems);
			cell.setColspan(1);
			table.addCell(cell);
			cell = new PdfPCell((new Phrase(((Integer)i).toString(),FontFactory.getFont("Arial",11,Font.BOLD))));
			cell.setColspan(4);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("Amount",FontFactory.getFont("Arial",11)));
			cell.setColspan(1);
			cell.setRowspan(2);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase(CurrentInvoice.getWordTotal().toUpperCase(),FontFactory.getFont("Arial",11)));
			cell.setColspan(4);
			cell.setRowspan(2);
			table.addCell(cell);
//			cell = new PdfPCell(new Paragraph("Remarks",FontFactory.getFont("Arial",11)));
//			cell.setColspan(1);
//			cell.setRowspan(2);
//			table.addCell(cell);
//			cell = new PdfPCell(new Phrase(remarks,FontFactory.getFont("Arial",11)));
//			cell.setColspan(4);
//			cell.setRowspan(2);
//			table.addCell(cell);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
	        table.setWidthPercentage(100);
			document.add(table);
			
			if(BillType == true){
				document.add(new Paragraph("\n\n                                                             Authorized Signatory | Khurana Store",FontFactory.getFont("Arial",11)));
			}
			
			document.close();
			
		}catch (FileNotFoundException | DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
//			clearScreen();
//			HomeFrame.setVisible(false);
//			View_Controller view = new View_Controller();
//			view.homeScreen();
		}
	}
	
	public class InvoiceCreationAction implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			char type = 'X';
			if(rbutton1.isSelected()){
				type = 'W';
			}
			else if(rbutton2.isSelected()){
				type = 'R';
			}
			String name = SentenceCase(CustomerNameField.getText());
			String contact = ContactField.getText();
			String Add = SentenceCase(Address.getText());
			CurrentInvoice = new Invoice(name,contact,Add,type);
			createInvoiceCreationScreen();
		}
		
	}
	
	public class EditNameAction implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			String s = (String)JOptionPane.showInputDialog(HomeFrame,"Enter New Product Name","Edit Product Name",JOptionPane.PLAIN_MESSAGE,null,null,null);
			if ((s != null) && s.length() > 0){
				EditProduct.setName(SentenceCase(s));
			}
			else{
				s = (String)JOptionPane.showInputDialog(HomeFrame,"Enter Valid Product Name","Edit Product Name",JOptionPane.PLAIN_MESSAGE,null,null,null);
			}
			AddtoFile();
			createEditItemScreen();
		}
		
	}
	
	public class EditMRPAction implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			String s = (String)JOptionPane.showInputDialog(HomeFrame,"Enter New MRP","Edit MRP",JOptionPane.PLAIN_MESSAGE,null,null,null);
			if ((s != null) && s.length() > 0){
				EditProduct.setMRP(Double.parseDouble(s));
			}
			else{
				s = (String)JOptionPane.showInputDialog(HomeFrame,"Enter Valid MRP","Edit MRP",JOptionPane.PLAIN_MESSAGE,null,null,null);
			}
			AddtoFile();
			createEditItemScreen();
		}
	}
	
	public class EditWholAction implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			String s = (String)JOptionPane.showInputDialog(HomeFrame,"Enter New Wholesale Rate","Edit Wholesale Rate",JOptionPane.PLAIN_MESSAGE,null,null,null);
			if ((s != null) && s.length() > 0){
				EditProduct.setWholesaleRate(Double.parseDouble(s));
			}
			else{
				s = (String)JOptionPane.showInputDialog(HomeFrame,"Enter Valid Wholesale Rate","Edit Wholesale Rate",JOptionPane.PLAIN_MESSAGE,null,null,null);
			}
			AddtoFile();
			createEditItemScreen();
		}
		
	}
	
	public class EditRetAction implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			String s = (String)JOptionPane.showInputDialog(HomeFrame,"Enter New Retail Rate","Edit Retail Rate",JOptionPane.PLAIN_MESSAGE,null,null,null);
			if ((s != null) && s.length() > 0){
				EditProduct.setRetailRate((Double.parseDouble(s)));
			}
			else{
				s = (String)JOptionPane.showInputDialog(HomeFrame,"Enter Valid Retail Rate","Edit Retail Rate",JOptionPane.PLAIN_MESSAGE,null,null,null);
			}
			AddtoFile();
			createEditItemScreen();
		}
		
	}
	
	public class DeleteItemAction implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			AllProducts.remove(EditProduct);
			AddtoFile();
			JOptionPane.showMessageDialog(HomeFrame,"Item Deleted Successfully");
			createViewAllItemsScreen();
		}
		
	}
	
	public class BackToViewAllItemsAction implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			createSearchItemScreen();
		}
		
	}
	
	public class SearchItemResultAction implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			
			AddItems();
			
			int i = 0, j = SearchResultsModel.getRowCount();
			for(j = SearchResultsModel.getRowCount() - 1; j >= 0; j--){
				SearchResultsModel.removeRow(j);
			}
			String Item = SearchItem.getText();
			i = 0;
			String[] s = Item.split(" ");
			ArrayList<String> search = new ArrayList<String>(Arrays.asList(s));
			ArrayList<String> Pro;
			int k = 0, l = 0;
			int flag = 0;
			SearchItemResultsList.clear();
			for(Product p : AllProducts){
				if(p.getName().toLowerCase().startsWith(Item)){
					if(!SearchItemResultsList.contains(p))
						SearchItemResultsList.add(p);
				}
			}
			for(Product p : AllProducts){
				Pro = new ArrayList<String>(Arrays.asList(p.getName().toLowerCase().split(" ")));
				for(String s1 : search){
					for(l = 0; l < Pro.size(); l++){
						if(Pro.get(l).equals(s1)){
							if(!SearchItemResultsList.contains(p))
								SearchItemResultsList.add(p);
						}
					}
				}
			}
			for(Product p : AllProducts){
				Pro = new ArrayList<String>(Arrays.asList(p.getName().toLowerCase().split(" ")));
				for(String s1 : search){
					flag = 0;
					for(l = 0; l < Pro.size(); l++){
						if(!Pro.get(l).startsWith(s1)){
							flag = 1;
						}
					}
					if(flag == 0){
						if(!SearchItemResultsList.contains(p))
							SearchItemResultsList.add(p);
					}
				}
			}
			for(Product p1 : AllProducts){
				flag = 0;
				for(String s1 : search){
					if(!p1.getName().toLowerCase().contains(s1)){
						flag = 1;
					}
				}
				if(flag == 0){
					if(!SearchItemResultsList.contains(p1))
						SearchItemResultsList.add(p1);
				}
			}
			for(Product p : AllProducts){
				Pro = new ArrayList<String>(Arrays.asList(p.getName().split(" ")));
				flag = 0;
				for(l = 0; l < Pro.size(); l++){
					for(k = 0; k < search.size(); k++){
						if(Pro.get(l).toLowerCase().startsWith(search.get(k).toLowerCase())){
							flag = 1;
						}
					}
				}
				if(flag == 1){
					if(!SearchItemResultsList.contains(p))
						SearchItemResultsList.add(p);
				}
			}
			for( i = 0; i < SearchItemResultsList.size(); i++){
				Product p = SearchItemResultsList.get(i);
				Object[] x = {Integer.toString(1 + i), p.getName(),Double.toString(p.getMRP()),Double.toString(p.getWholesaleRate()),Double.toString(p.getRetailRate())};
				SearchResultsModel.addRow(x);
			}
			SearchItem.setText("");
		}
		
	}
	
	public class SearchItemInvoiceAction implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			
			AddItems();
			
			ListSelectionModel cellSelectionModel = SearchResultsTableInvoice.getSelectionModel();
			cellSelectionModel.removeListSelectionListener(listenetSearchResults);
			int j = 0;
			SearchItemInvoiceList.clear();
			SearchResultsTableInvoice.clearSelection();
			String Item = SearchItemForInvoice.getText().toLowerCase();
			String[] s = Item.split(" ");
			ArrayList<String> search = new ArrayList<String>(Arrays.asList(s));
			ArrayList<String> Pro;
			int i = 0, l = 0;
			SearchItemInvoiceList.clear();
			int flag = 0;
//			for(Product p : AllProducts){
//				Pro = new ArrayList<String>(Arrays.asList(p.getName().toLowerCase().split(" ")));
//				flag = 0;
//				for(i = 0; i < search.size(); i++){
//					for(l = 0; l < Pro.size(); l++){
//						if(Pro.get(l).startsWith(search.get(i))){
//							
//						}
//					}
//				}
//			}
			for(Product p : AllProducts){
				if(p.getName().toLowerCase().startsWith(Item)){
					if(!SearchItemInvoiceList.contains(p))
						SearchItemInvoiceList.add(p);
				}
			}
			for(Product p : AllProducts){
				Pro = new ArrayList<String>(Arrays.asList(p.getName().toLowerCase().split(" ")));
				for(String s1 : search){
					for(l = 0; l < Pro.size(); l++){
						if(Pro.get(l).equals(s1)){
							if(!SearchItemInvoiceList.contains(p))
								SearchItemInvoiceList.add(p);
						}
					}
				}
			}
			for(Product p : AllProducts){
				Pro = new ArrayList<String>(Arrays.asList(p.getName().toLowerCase().split(" ")));
				for(String s1 : search){
					flag = 0;
					for(l = 0; l < Pro.size(); l++){
						if(!Pro.get(l).startsWith(s1)){
							flag = 1;
						}
					}
					if(flag == 0){
						if(!SearchItemInvoiceList.contains(p))
							SearchItemInvoiceList.add(p);
					}
				}
			}
			for(Product p1 : AllProducts){
				flag = 0;
				for(String s1 : search){
					if(!p1.getName().toLowerCase().contains(s1)){
						flag = 1;
					}
				}
				if(flag == 0){
					if(!SearchItemInvoiceList.contains(p1))
						SearchItemInvoiceList.add(p1);
				}
			}
			for(Product p : AllProducts){
				Pro = new ArrayList<String>(Arrays.asList(p.getName().split(" ")));
				flag = 0;
				for(l = 0; l < Pro.size(); l++){
					for(i = 0; i < search.size(); i++){
						if(Pro.get(l).toLowerCase().startsWith(search.get(i).toLowerCase())){
							flag = 1;
						}
					}
				}
				if(flag == 1){
					if(!SearchItemInvoiceList.contains(p))
						SearchItemInvoiceList.add(p);
				}
			}
			for(j = (SearchResultTableModel.getRowCount() - 1); j >= 0;  j--){
				SearchResultTableModel.removeRow(j);
			}
			int k = 0;
			for(Product p2 : SearchItemInvoiceList){
					Object[] x = {Integer.toString(1 + k), p2.getName(),Double.toString(p2.getMRP()),Double.toString(p2.getWholesaleRate()),Double.toString(p2.getRetailRate())};
					SearchResultTableModel.addRow(x);
					k++;
			}
			cellSelectionModel.addListSelectionListener(listenetSearchResults);
			SearchItemForInvoice.setText("");
			AutoUpdateSuggestions(Item);
			//createInvoiceCreationScreen();
		}
	}
	
	public String SentenceCase(String s){
		
		if(s.equals("") || s == null){
			return ("");
		}
		else{
			
			String[] words = s.split(" ");
			
			for(int i = 0; i< words.length; i++){
				words[i] = Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1);
			}
			String out = "";
			for(int i = 0; i<words.length; i++){
				out += words[i] + " ";
			}
			return(out);
		}
	}
	
	public void AddtoFile(){
//		try{
//			PrintWriter Writer = new PrintWriter(new BufferedWriter(new FileWriter("/Users/Sagar/Desktop/Items.txt")));
//			int i = 0;
//			Product p;
//			String Name = "Product Name", Whol = "Wholesale Rate", Ret = "Retail Rate", MRP = "MRP";
//			Writer.printf("%-50s | %-15s | %-15s | %-15s\n",Name,MRP,Whol,Ret);
//			//System.out.printf("%-50s | %-15s | %-15s | %-15s\n",Name,MRP,Whol,Ret);
//			for(i = 0; i < AllProducts.size(); i++){
//				p = AllProducts.get(i);
//				Writer.printf("%-50s | %-15.2f | %-15.2f | %-15.2f\n",p.getName(),p.getMRP(),p.getWholesaleRate(),p.getRetailRate());
//				//System.out.printf("%-50s | %-15.2f | %-15.2f | %-15.2f\n",p.getName(),p.getMRP(),p.getWholesaleRate(),p.getRetailRate());
//			}
//			Writer.close();
//		}catch(FileNotFoundException e1){
//			e1.printStackTrace();
//		}catch(IOException e2){
//			e2.printStackTrace();
//		}
		
		FileWriter fw;
		try{
			fw = new FileWriter("C:/Users/Khurana Store/Desktop/Khurana Store/Docs/Items.csv");
			int i = 0;
			Product p;
			fw.append("Product Name,MRP,Wholesale Rate,Retail Rate\n");
			
			for(i = 0; i < AllProducts.size(); i++){
				p = AllProducts.get(i);
				fw.append(p.getCSV());
			}
			fw.close();
		}catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public class listSelectionListener implements ListSelectionListener{
	      public void valueChanged(ListSelectionEvent e) {

	    	  	ListSelectionModel cellSelection = InvoiceItemsTable.getSelectionModel();
	    	  	cellSelection.removeListSelectionListener(listenerSearchResults);
	    	  	
	    	  	
		        int[] selectedRow = SearchResultsTableInvoice.getSelectedRows();
		        AddItemtoInvoice(selectedRow[0]);
		        SearchItemForInvoice.requestFocus();
		        cellSelection.addListSelectionListener(listenerSearchResults);
	      }
	}
	
	public void AddItemtoInvoice(int rownum) {
		Product p = SearchItemInvoiceList.get(rownum);
        String s = (String)JOptionPane.showInputDialog(HomeFrame,"Enter Quantity","Quantity",JOptionPane.PLAIN_MESSAGE,null,null,null);
        if(s == null || s.length() == 0){
        	;
        }else{
        	int quantity = Integer.parseInt(s);
        	CurrentInvoice.addProduct(p,quantity);
        	InvoiceTotalLabel.setText("Invoice Total : " + CurrentInvoice.getTotal());
        	//createInvoiceCreationScreen();
        	int i = 0;
        	for( i = (InvoiceItemsModel.getRowCount() - 1); i >= 0 ; i--){
        		InvoiceItemsModel.removeRow(i);
        	}
        	CurrentInvoiceItemList.clear();
        	int k = 0;
        	for(Entry<Product,Integer> entry : CurrentInvoice.getListOfProducts().entrySet()){
        	
        		CurrentInvoiceItemList.add(entry.getKey());
        		
	        	if(CurrentInvoice.getType() == 'W'){
	        		Object[] Whol = {Integer.toString( 1 + k ),entry.getKey().getName(),entry.getValue(),entry.getKey().getWholesaleRate(),(entry.getKey().getWholesaleRate()*entry.getValue())};
        			InvoiceItemsModel.addRow(Whol);
	        	}else{
	        		Object[] Ret = {Integer.toString( 1 + k ),entry.getKey().getName(),entry.getValue(),entry.getKey().getRetailRate(),(entry.getKey().getRetailRate()*entry.getValue())};
        			InvoiceItemsModel.addRow(Ret);
	        	}
        		k++;
        	}
        	SearchItemForInvoice.setText("");
        }
	}
	
	
	public class DeleteInvoiceItemListener implements ListSelectionListener{
		
		public void valueChanged(ListSelectionEvent e){
			
			ListSelectionModel cellSelection = InvoiceItemsTable.getSelectionModel();
    	  	cellSelection.removeListSelectionListener(listenerSearchResults);
			
			int[] selectedRow = InvoiceItemsTable.getSelectedRows();
//			System.out.println(selectedRow[0]);
//			System.out.println(CurrentInvoiceItemList.get(selectedRow[0]));
			Product p = CurrentInvoiceItemList.get(selectedRow[0]);
			CurrentInvoice.DeleteProduct(p);
			JOptionPane.showMessageDialog(HomeFrame,"Item Deleted Successfully");
			int i = 0;
        	for( i = (InvoiceItemsModel.getRowCount() - 1); i >= 0 ; i--){
        		InvoiceItemsModel.removeRow(i);
        	}
        	CurrentInvoiceItemList.clear();
			int k = 0;
        	for(Entry<Product,Integer> entry : new TreeMap<Product,Integer>(CurrentInvoice.getListOfProducts()).entrySet()){
        	
        		CurrentInvoiceItemList.add(entry.getKey());
        		
	        	if(CurrentInvoice.getType() == 'W'){
	        		Object[] Whol = {Integer.toString( 1 + k ),entry.getKey().getName(),entry.getValue(),entry.getKey().getWholesaleRate(),(entry.getKey().getWholesaleRate()*entry.getValue())};
        			InvoiceItemsModel.addRow(Whol);
	        	}else{
	        		Object[] Ret = {Integer.toString( 1 + k ),entry.getKey().getName(),entry.getValue(),entry.getKey().getRetailRate(),(entry.getKey().getRetailRate()*entry.getValue())};
        			InvoiceItemsModel.addRow(Ret);
	        	}
        		k++;
        	}
			InvoiceTotalLabel.setText("Invoice Total : " + CurrentInvoice.getTotal());
		
			cellSelection.addListSelectionListener(listenerSearchResults);
			
			SearchItemForInvoice.requestFocus();
			
		}
		
	}
	
	
	public class newlistSelectionListener implements ListSelectionListener{
	      public void valueChanged(ListSelectionEvent e) {

	    	  ListSelectionModel cellSelection = SearchItemResults.getSelectionModel();
	    	  	cellSelection.removeListSelectionListener(n);
	    	  
	        int[] selectedRow = SearchItemResults.getSelectedRows();
	        	EditProduct = SearchItemResultsList.get(selectedRow[0]);
	        	createEditItemScreen();
	        	SearchItemResults.clearSelection();
	        	
	        	cellSelection.addListSelectionListener(n);
	        	
	      }
	    }
	
	public class PDFPageEventAddInvoiceName extends PdfPageEventHelper{
		
		public void onStartPage(PdfWriter writer, Document document){
			Paragraph p = new Paragraph(CurrentInvoice.getCustomerName(),FontFactory.getFont("Arial",11,Font.BOLD));
			p.setAlignment(Element.ALIGN_RIGHT);
			try {
				document.add(p);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public class AddingItemAction implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			
			String Item_Name = SentenceCase(ProductName.getText());
			double MRPRs = Double.parseDouble(MRP.getText());
			double Wholesale = Double.parseDouble(WholesalePrice.getText());
			double RetailPriceRate = Double.parseDouble(RetailPrice.getText());
			
			Product p = new Product(Item_Name,Wholesale,RetailPriceRate,MRPRs);
			boolean x = AllProducts.add(p);
//			System.out.println("Added");
			if(x){
				AddItemHeaderLabel.setText("Item Added");
			}
			else{
				AddItemHeaderLabel.setText("Item Already Exist");
			}
			
			AddtoFile();
			
			createAddItemScreen();
			
		}
	}

	public class CreateItemsPdfAction implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			
			String outputFile = "C:/Users/Khurana Store/Desktop/Khurana Store/Docs/Items.pdf";
			Document document = new Document(PageSize.A5);
			try {
				@SuppressWarnings("unused")
				PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(outputFile));
				document.setMargins(5f,5f,5f,5f);
				document.open();
				PdfPTable table = new PdfPTable(8);
				PdfPCell cell = new PdfPCell(new Phrase("Product Name"));
				cell.setColspan(4);
				table.setWidthPercentage(100f);
				table.addCell("SNo.");
				table.addCell(cell);
				table.addCell("MRP");
				table.addCell("Wholesale Price");
				table.addCell("Retail Price");
				
				int i = 0;
				for(Product p : AllProducts){
					cell = new PdfPCell(new Phrase(p.getName()));
					cell.setColspan(4);
					table.addCell(Integer.toString(i + 1));
					table.addCell(cell);
					table.addCell(Double.toString(p.getMRP()));
					table.addCell(Double.toString(p.getWholesaleRate()));
					table.addCell(Double.toString(p.getRetailRate()));
					i++;
				}
				document.add(table);
				document.close();
			}catch (FileNotFoundException | DocumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}finally{
				clearScreen();
				homeScreen();
			}
		}
	}
	
	@SuppressWarnings({ "serial", "rawtypes" })
	public class AutoCompleteComboBox extends JComboBox
	{
		   public int caretPos=0;
		   public JTextField inputField=null;
		  
		   @SuppressWarnings("unchecked")
		public AutoCompleteComboBox(final Object elements[]) {
		      super(elements);
		      setEditor(new BasicComboBoxEditor());
		      setEditable(true);
		   }
		  
		   public void setSelectedIndex(int index) {
		      super.setSelectedIndex(index);
		      
		      SearchItemForInvoice.setText(getItemAt(index).toString());
		      SearchItemInvoiceButton.doClick();
		      
		      inputField.setText(getItemAt(index).toString());
		      inputField.setSelectionEnd(caretPos + inputField.getText().length());
		      inputField.moveCaretPosition(caretPos);
		   }
		  
		   public void setEditor(ComboBoxEditor editor) {
		      super.setEditor(editor);
		      if (editor.getEditorComponent() instanceof JTextField) {
		         inputField = (JTextField) editor.getEditorComponent();
		  
		         inputField.addKeyListener(new KeyAdapter() {
		            public void keyReleased( KeyEvent ev ) {
		               char key=ev.getKeyChar();
		               if (! (Character.isLetterOrDigit(key) || Character.isSpaceChar(key) )) return;
		  
		               caretPos=inputField.getCaretPosition();
		               String text="";
		               try {
		                  text=inputField.getText(0, caretPos);
		               }
		               catch (javax.swing.text.BadLocationException e) {
		                  e.printStackTrace();
		               }
		  
		               for (int i=0; i<getItemCount(); i++) {
		                  String element = (String) getItemAt(i);
		                  if (element.toLowerCase().startsWith(text.toLowerCase())) {
		                     setSelectedIndex(i);
		                     return;
		                  }
		               }
		            }
		         });
		      }
		   }
		}
}