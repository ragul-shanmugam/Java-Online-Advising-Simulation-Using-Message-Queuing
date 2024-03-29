/*
Name: Ragul Shanmugam
Student ID: 1001657250
*/

/* Code references:
	https://www.geeksforgeeks.org/introducing-threads-socket-programming-java/
	https://www.geeksforgeeks.org/multi-threaded-chat-application-set-2/
*/

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ragul
 */
public class MQServer extends javax.swing.JFrame {

    /**
     * Creates new form MQServer
     */
    public MQServer() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Message Queuing Server");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton3.setText("Stop");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 268, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        jTextArea1.append("MQ Server started...\n");
        jButton1.setEnabled(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton3ActionPerformed

    // Vector to store active clients 
    static Vector<ClientHandler> activeClients = new Vector<>();

    //ArrayList to store the Student Name, Course and Approval details
    // static ArrayList<String> studentDecisionDetails = new ArrayList<String>();
    public static HashMap<String, String> studentDecisionDetails = new HashMap<String, String>();

    public static HashMap<String, String> advisorMap = new HashMap<String, String>();

    // counter for clients 
    static int i = 0;

    static String userDetails;
    static DataInputStream dis;
    static DataOutputStream dos;

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MQServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MQServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MQServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MQServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MQServer().setVisible(true);
            }
        });

        try {

            // server is listening on port 9000
            ServerSocket serverSocket = new ServerSocket(9000);

            Socket socket;

            // running infinite loop for getting 
            // client request 
            while (true) {

                // Accept the incoming request 
                socket = serverSocket.accept();

                // obtain input and output streams 
                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());

                userDetails = dis.readUTF();

                jTextArea1.append("New client request received : " + userDetails + "\n");

                // Create a new handler object for handling this request. 
                ClientHandler mtch = new ClientHandler(socket, userDetails, dis, dos);

                // Create a new Thread with this object. 
                Thread t = new Thread(mtch);

                // add this client to active clients list 
                activeClients.add(mtch);
                // start the thread. 
                t.start();

                // increment i for new client. 
                // i is used for naming only, and can be replaced 
                // by any naming scheme 
                i++;

            }
        } catch (Exception e) {
            System.err.println("");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    static javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
// ClientHandler class 

class ClientHandler implements Runnable {

    final DataInputStream dis;
    final DataOutputStream dos;
    public Socket socket;
    final String userName;
    public boolean isLoggedIn;
    public String received;
    public String studentName;
    public String courseName;
    public StringTokenizer st;
    public String clientid;
    MQInterface mqinterface;

    // constructor 
    public ClientHandler(Socket socket, String userName, DataInputStream dis, DataOutputStream dos) {
        this.dis = dis;
        this.dos = dos;
        this.userName = userName;
        this.socket = socket;
        this.isLoggedIn = true;
    }

    @Override
    public void run() {

        while (this.isLoggedIn) {
            try {

                // receive the string 
                received = dis.readUTF();
                /*If the advisor process polls and the map is empty, server will tell it to
                wait for 3 seconds and poll again*/
                if (received.equalsIgnoreCase("advisor")) {
                    if (MQServer.studentDecisionDetails.isEmpty()) {
                        dos.writeUTF("wait");
                    }
                    if (!MQServer.studentDecisionDetails.isEmpty()) {
                        Set<String> key = MQServer.studentDecisionDetails.keySet();
                        Iterator<String> i = key.iterator();
                        while (i.hasNext()) {
                            String sName = i.next();
                            String cName = MQServer.studentDecisionDetails.get(sName);
                            String finalString = sName + "/" + cName;
                            MQServer.studentDecisionDetails.remove(sName);
                            dos.writeUTF(finalString);
                        }
                    }
                }

                /*If the notifier process polls and the map is empty, server will tell it to
                wait for 7 seconds and poll again*/
                if (received.equalsIgnoreCase("notifier")) {
                    if (MQServer.advisorMap.isEmpty()) {
                        dos.writeUTF("wait");
                    }
                    if (!MQServer.advisorMap.isEmpty()) {
                        Set<String> key = MQServer.advisorMap.keySet();
                        Iterator<String> i = key.iterator();
                        while (i.hasNext()) {
                            String sName = i.next();
                            String cName = MQServer.advisorMap.get(sName);
                            String finalString = sName + "#" + cName;
                            MQServer.advisorMap.remove(sName);
                            dos.writeUTF(finalString);
                        }
                    }
                } /*After we get result from the students and the advisors we use this else block to 
                put the values in the corresponding maps*/ else {
                    // break the string into message and recipient part 
                    st = new StringTokenizer(received, "/");
                    while (st.hasMoreTokens()) {
                        studentName = st.nextToken();
                        courseName = st.nextToken();
                        if (courseName.contains("#")) {
                            MQServer.advisorMap.put(studentName, courseName);
                            mqinterface.advisorDecisionDetails.put(studentName, courseName);
                        } else {
                            MQServer.studentDecisionDetails.put(studentName, courseName);
                            mqinterface.studentCourseDetails.put(studentName, courseName);
                        }
                        break;
                    }
                }
            } catch (Exception e) {
            }
        }
        try {
            // closing resources 
            this.dis.close();
            this.dos.close();

        } catch (Exception e) {
        }
    }
}
