/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Shell;

import FileIO.ShellIO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Nicolas
 */
public class Shell extends JFrame {

    private ShellIO shell_file_sys;
    private LinkedList<String> users;
    private LinkedList<String> instances;
    private Dimension screen_resolution;
    private JMenuBar menu_bar;
    private JMenu options;
    private JMenu about;
    private JPanel main_panel;
    private JTabbedPane tab_pane;
    private JPanel tab_panel;
    private JScrollPane connections_scroll;
    private JScrollPane instances_scroll;
    private JScrollPane server_scroll;
    private JScrollPane scroll_pane;
    private JTextArea server_text_label;
    private JTextArea text_area;
    private JTable conn_table;
    private JTable instances_table;
    private JPanel search_panel;
    private JTextField search_input;
    private JButton search_buton;
    private String date;

    public Shell() {
        initComponents();
        this.setVisible(true);
    }

    public void initComponents() {
        instances = new LinkedList<String>();
        users = new LinkedList<String>();
        Calendar rightNow = Calendar.getInstance();
        setDate(rightNow.get(Calendar.DAY_OF_MONTH) + "-" + rightNow.get(Calendar.MONTH) + "-" + rightNow.get(Calendar.YEAR) + "(" + rightNow.get(Calendar.HOUR_OF_DAY) + "." + rightNow.get(Calendar.MINUTE) + "." + rightNow.get(Calendar.SECOND) + ")");
        setShell_file_sys(new ShellIO(getDate()));
        getShell_file_sys().initShellLogs();
        getShell_file_sys().initServerLog();
        setScreen_resolution(getScreenResolution());
        setTitle("Rotten City Server");
        ImageIcon img = new ImageIcon("src/Images/icon.png");
        setIconImage(img.getImage());
        setMain_panel(new JPanel());
        setTab_panel(new JPanel());
        setTab_pane(new JTabbedPane());
        setServer_scroll(new JScrollPane());
        setServer_text_label(new JTextArea());
        setConnections_scroll(new JScrollPane());
        setConn_table(new JTable());
        setInstances_scroll(new JScrollPane());
        setInstances_table(new JTable());
        setScroll_pane(new JScrollPane());
        setText_area(new JTextArea());
        setMenu_bar(new JMenuBar());
        setOptions(new JMenu());
        setAbout(new JMenu());
        setSearch_panel(new JPanel());

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(getScreen_resolution());
        setMinimumSize(new Dimension(705, 475));
        setPreferredSize(new Dimension(705, 475));

        setName("ShellFrame"); // NOI18N

        getMain_panel().setLayout(new BorderLayout());
        getTab_panel().setLayout(new BorderLayout());
        getSearch_panel().setLayout(new BorderLayout());

        getTab_pane().setMaximumSize(new Dimension(205, getScreen_resolution().height - 150));
        getTab_pane().setMinimumSize(new Dimension(205, 325));
        getTab_pane().setPreferredSize(new Dimension(205, 325));
        getTab_pane().setFont(new Font("Courier New", 1, 12));

        getSearch_panel().setMaximumSize(new Dimension(205, 150));
        getSearch_panel().setMinimumSize(new Dimension(205, 150));
        getSearch_panel().setPreferredSize(new Dimension(205, 150));
        getSearch_panel().add(new JTextArea("SEARCH PANEL"), BorderLayout.CENTER);

        getText_area().setBackground(Color.BLACK);
        getText_area().setForeground(Color.GREEN);
        getText_area().setMaximumSize(new Dimension(getScreen_resolution().width - 205, getScreen_resolution().height));
        getText_area().setMinimumSize(new Dimension(500, 475));
        getText_area().setPreferredSize(new Dimension(500, 475));


        getServer_text_label().setText(serverInfo());
        getServer_text_label().setForeground(Color.GREEN);
        getServer_text_label().setFont(new Font("Courier New", 1, 9));


        getServer_scroll().setViewportView(getServer_text_label());

        getTab_pane().addTab("Server", getServer_scroll());

        getConn_table().setModel(new DefaultTableModel(
                new Object[][]{
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""}
                },
                new String[]{
                    "Connection IP", "User"
                }) {
            Class[] types = new Class[]{
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean[]{
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        getConnections_scroll().setViewportView(getConn_table());

        getTab_pane().addTab("Conn", getConnections_scroll());
        getInstances_table().setModel(new DefaultTableModel(
                new Object[][]{
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""},
                    {"", ""}
                },
                new String[]{
                    "Instance Name", "Status"
                }) {
            Class[] types = new Class[]{
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean[]{
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        getInstances_scroll().setViewportView(getInstances_table());

        getTab_pane().addTab("Instances", getInstances_scroll());

        getTab_panel().add(getTab_pane(), BorderLayout.CENTER);
        getTab_panel().add(getSearch_panel(), BorderLayout.SOUTH);

        getMain_panel().add(getTab_panel(), BorderLayout.LINE_START);


        getScroll_pane().setViewportView(getText_area());

        getMain_panel().add(getScroll_pane(), BorderLayout.CENTER);

        getContentPane().add(getMain_panel(), BorderLayout.CENTER);

        getOptions().setText("Options");
        getMenu_bar().add(getOptions());

        getAbout().setText("About");
        getMenu_bar().add(getAbout());

        setJMenuBar(getMenu_bar());

        //LISTENERS

        getTab_pane().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tab_paneClicked(evt);
            }
        });

        getInstances_table().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                instances_tableClicked(evt);
            }
        });

        getConn_table().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                conn_tableClicked(evt);
            }
        });

        pack();

        this.addWindowListener(new WindowAdapter() {
            /*
             * Manejo del evento EXIT (X)
             */
            @Override
            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }
        });


    }

    private Dimension getScreenResolution() {
        Toolkit t = Toolkit.getDefaultToolkit();
        return t.getScreenSize();
    }

    private static ImageIcon createImageIcon(String path) {
        if (path != null) {
            return new ImageIcon(path);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public String serverInfo() {
        return "ROTTEN CITY SERVER v 0.0 \n"
                + "4:20 Entertainment\n"
                + "\n"
                + "                 0 \n"
                + "                000 \n"
                + "               00000  \n"
                + "               00000  \n"
                + "   00          00000          00 \n"
                + "    0000      0000000      0000  \n"
                + "    000000    0000000    000000  \n"
                + "     000000   0000000   000000  \n"
                + "      0000000 0000000 0000000  \n"
                + "        000000 00000 000000  \n"
                + "0000     000000 000 0000  000000000\n"
                + " 000000000  0000 0 000 000000000  \n"
                + "    000000000  0 0 0 000000000  \n"
                + "         0000000000000000  \n"
                + "             000 0 0000  \n"
                + "           00000 0  00000  \n"
                + "          00     0      00  \n"
                + ""
                + "         .,; \n"
                + "      ';,.'      ';.,' \n"
                + "              ;,.;' \n"
                + "          ;.,:   '.,;, \n"
                + "      ',.  .',;;.',; \n"
                + "    ____________ \n"
                + "   \\oooooooooo/ \n"
                + "    \\________/ \n"
                + "     {________} \n"
                + "     \\______/ \n"
                + "       ',__,' \n"
                + "        |oo| \n"
                + "        |oo|    _____ \n"
                + "        |==|   / ___() \n"
                + "        |==|  / / \n"
                + "        |oo| / / \n"
                + "        |oo|/ / \n"
                + "        |==/ / \n"
                + "        |='./ \n"
                + "        |oo| \n"
                + "        |==| \n"
                + "        |__| \n"
                + "      ,'____', \n"
                + "     /'________'\\ \n"
                + "    /____________\\ \n";

    }

    public void write(String input, ShellProfile profile) {
        profile.write(input, this);
        switch (profile.getProfile()) {
            case "server":
                if (getTab_pane().getTitleAt(getTab_pane().getSelectedIndex()).equals("Server")) {
                    updateDisplayText("server", "");
                }
                break;
            case "instance":
                InstanceProfile profI = (InstanceProfile) profile;
                if (getInstances_table().getSelectedRow() >= 0 && getInstances_table().getSelectedColumn() >= 0 && getTab_pane().getTitleAt(getTab_pane().getSelectedIndex()).equals("Instances") && getInstances_table().getValueAt(getInstances_table().getSelectedRow(), getInstances_table().getSelectedColumn()).toString().equals(profI.getMap_id())) {
                    updateDisplayText("instance", profI.getMap_id());
                }
                break;
            case "user":   
                UserProfile profU = (UserProfile) profile;
                if (getConn_table().getSelectedRow() >= 0 && getConn_table().getSelectedColumn() >= 0 && getTab_pane().getTitleAt(getTab_pane().getSelectedIndex()).equals("Conn") && getConn_table().getValueAt(getInstances_table().getSelectedRow(), 1).toString().equals(profU.getUser_name())) {
                    updateDisplayText("conn", profU.getUser_name());
                }
                break;
            default:

                break;
        }
        //getText_area().setRows(getText_area().getRows()+1);
    }

    public void updateDisplayText(String n, String m) {
        getText_area().setText("");
        switch (n) {
            case "server":
                getText_area().append(getShell_file_sys().getServerLog());
                break;
            case "instance":
                getText_area().append(getShell_file_sys().getInstanceLog(m));
                break;
            case "conn":
                getText_area().append(getShell_file_sys().getUserLog(m));
                break;
        }

    }

    public void addInstance(String map_id) {
        instances.add(map_id);
        upDateInstancesTable();
    }

    public void addConn(String user) {
        users.add(user);
        upDateUsersTable();
    }

    public void upDateInstancesTable() {
        Iterator it = instances.iterator();
        String map_id = "";
        int row = 0;
        while (it.hasNext()) {
            map_id = it.next().toString();
            instances_table.setValueAt(map_id, row, 0);
            instances_table.setValueAt("online", row, 1);
            row++;
        }
    }

    public void upDateUsersTable() {
        Iterator it = users.iterator();
        String[] user;
        int row = 0;
        while (it.hasNext()) {
            user = it.next().toString().split("#");   
            conn_table.setValueAt(user[1].toString(), row, 0);
            conn_table.setValueAt(user[0].toString(), row, 1);
            row++;
        }
    }

    //EVENTOS 
    private void tab_paneClicked(MouseEvent evt) {
        String selected_tab = getTab_pane().getTitleAt(getTab_pane().getSelectedIndex());
        switch (selected_tab) {
            case "Server":
                updateDisplayText("server", "");
                break;
            case "Instances":
                instances_tableClicked(evt);
                break;
                case "Conn":
                conn_tableClicked(evt);
                break;
            default:
                break;
        }
    }

    private void instances_tableClicked(MouseEvent evt) {
        if (getInstances_table().getSelectedRow() >= 0 && getInstances_table().getSelectedColumn() >= 0) {
            String selected_instance = getInstances_table().getValueAt(getInstances_table().getSelectedRow(), 0).toString();
            if (!selected_instance.equals("")) {
                updateDisplayText("instance", selected_instance);
            }
        }
    }

    private void conn_tableClicked(MouseEvent evt) {
        if (getConn_table().getSelectedRow() >= 0 && getConn_table().getSelectedColumn() >= 0) {
            String selected_conn = getConn_table().getValueAt(getConn_table().getSelectedRow(), 1).toString();
            if (!selected_conn.equals("")) {
                updateDisplayText("conn", selected_conn);
            }
        }
    }

    /**
     * @return the shell_file_sys
     */
    public ShellIO getShell_file_sys() {
        return shell_file_sys;
    }

    /**
     * @param shell_file_sys the shell_file_sys to set
     */
    public void setShell_file_sys(ShellIO shell_file_sys) {
        this.shell_file_sys = shell_file_sys;
    }

    /**
     * @return the users
     */
    public LinkedList<String> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(LinkedList<String> users) {
        this.users = users;
    }

    /**
     * @return the instances
     */
    public LinkedList<String> getInstances() {
        return instances;
    }

    /**
     * @param instances the instances to set
     */
    public void setInstances(LinkedList<String> instances) {
        this.instances = instances;
    }

    /**
     * @return the screen_resolution
     */
    public Dimension getScreen_resolution() {
        return screen_resolution;
    }

    /**
     * @param screen_resolution the screen_resolution to set
     */
    public void setScreen_resolution(Dimension screen_resolution) {
        this.screen_resolution = screen_resolution;
    }

    /**
     * @return the menu_bar
     */
    public JMenuBar getMenu_bar() {
        return menu_bar;
    }

    /**
     * @param menu_bar the menu_bar to set
     */
    public void setMenu_bar(JMenuBar menu_bar) {
        this.menu_bar = menu_bar;
    }

    /**
     * @return the options
     */
    public JMenu getOptions() {
        return options;
    }

    /**
     * @param options the options to set
     */
    public void setOptions(JMenu options) {
        this.options = options;
    }

    /**
     * @return the about
     */
    public JMenu getAbout() {
        return about;
    }

    /**
     * @param about the about to set
     */
    public void setAbout(JMenu about) {
        this.about = about;
    }

    /**
     * @return the main_panel
     */
    public JPanel getMain_panel() {
        return main_panel;
    }

    /**
     * @param main_panel the main_panel to set
     */
    public void setMain_panel(JPanel main_panel) {
        this.main_panel = main_panel;
    }

    /**
     * @return the tab_pane
     */
    public JTabbedPane getTab_pane() {
        return tab_pane;
    }

    /**
     * @param tab_pane the tab_pane to set
     */
    public void setTab_pane(JTabbedPane tab_pane) {
        this.tab_pane = tab_pane;
    }

    /**
     * @return the tab_panel
     */
    public JPanel getTab_panel() {
        return tab_panel;
    }

    /**
     * @param tab_panel the tab_panel to set
     */
    public void setTab_panel(JPanel tab_panel) {
        this.tab_panel = tab_panel;
    }

    /**
     * @return the connections_scroll
     */
    public JScrollPane getConnections_scroll() {
        return connections_scroll;
    }

    /**
     * @param connections_scroll the connections_scroll to set
     */
    public void setConnections_scroll(JScrollPane connections_scroll) {
        this.connections_scroll = connections_scroll;
    }

    /**
     * @return the instances_scroll
     */
    public JScrollPane getInstances_scroll() {
        return instances_scroll;
    }

    /**
     * @param instances_scroll the instances_scroll to set
     */
    public void setInstances_scroll(JScrollPane instances_scroll) {
        this.instances_scroll = instances_scroll;
    }

    /**
     * @return the server_scroll
     */
    public JScrollPane getServer_scroll() {
        return server_scroll;
    }

    /**
     * @param server_scroll the server_scroll to set
     */
    public void setServer_scroll(JScrollPane server_scroll) {
        this.server_scroll = server_scroll;
    }

    /**
     * @return the scroll_pane
     */
    public JScrollPane getScroll_pane() {
        return scroll_pane;
    }

    /**
     * @param scroll_pane the scroll_pane to set
     */
    public void setScroll_pane(JScrollPane scroll_pane) {
        this.scroll_pane = scroll_pane;
    }

    /**
     * @return the server_text_label
     */
    public JTextArea getServer_text_label() {
        return server_text_label;
    }

    /**
     * @param server_text_label the server_text_label to set
     */
    public void setServer_text_label(JTextArea server_text_label) {
        this.server_text_label = server_text_label;
    }

    /**
     * @return the text_area
     */
    public JTextArea getText_area() {
        return text_area;
    }

    /**
     * @param text_area the text_area to set
     */
    public void setText_area(JTextArea text_area) {
        this.text_area = text_area;
    }

    /**
     * @return the conn_table
     */
    public JTable getConn_table() {
        return conn_table;
    }

    /**
     * @param conn_table the conn_table to set
     */
    public void setConn_table(JTable conn_table) {
        this.conn_table = conn_table;
    }

    /**
     * @return the instances_table
     */
    public JTable getInstances_table() {
        return instances_table;
    }

    /**
     * @param instances_table the instances_table to set
     */
    public void setInstances_table(JTable instances_table) {
        this.instances_table = instances_table;
    }

    /**
     * @return the search_panel
     */
    public JPanel getSearch_panel() {
        return search_panel;
    }

    /**
     * @param search_panel the search_panel to set
     */
    public void setSearch_panel(JPanel search_panel) {
        this.search_panel = search_panel;
    }

    /**
     * @return the search_input
     */
    public JTextField getSearch_input() {
        return search_input;
    }

    /**
     * @param search_input the search_input to set
     */
    public void setSearch_input(JTextField search_input) {
        this.search_input = search_input;
    }

    /**
     * @return the search_buton
     */
    public JButton getSearch_buton() {
        return search_buton;
    }

    /**
     * @param search_buton the search_buton to set
     */
    public void setSearch_buton(JButton search_buton) {
        this.search_buton = search_buton;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }
}
