package twoslits;

/*
 * Utilities.java
 * Nick
 */



import java.io.*;
import java.awt.*;
import java.awt.Color;
import javax.swing.*;
import javax.swing.text.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.mail.*;
import javax.mail.internet.*;
import com.sun.mail.smtp.*;
import javax.mail.internet.InternetAddress;
import java.util.Properties;
import java.util.zip.*;
import org.apache.commons.net.ftp.*;
import java.net.URI;
import java.sql.*;
/**
 *
 * @author nharkiolakis
 */
public class Utilities {
  
  private static final int X=0,Y=1,Z=2;
  public static final String OK = "OK";
  static JFrame frame;
  static JTextField jTextField;
  static JButton jButton_Ok;
  static JButton jButton_Cancel;
  public static String returnText;
  public static String[] returnTextArray = new String[10];
  public static double returnDouble;
  public static int returnInt,returnStandard,returnSubStandard;
  public static Color returnColor;
  public static File returnFile;
  static Desktop desktop = null;

  // ---------------------------------------------------------------------------
  public Utilities() { }
  // ---------------------------------------------------------------------------
  public static String stripString(String s) {  
    String good =
      ".,/-+!@#$%^&*() abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    String result = "";
    for ( int i = 0; i < s.length(); i++ ) {
        if ( good.indexOf(s.charAt(i)) >= 0 ) result += s.charAt(i);
        else result += " ";
        }
    return result;
    }
  // ---------------------------------------------------------------------------
  public static ImageIcon getIconFromURL(String myURL) {
    try {
      return new javax.swing.ImageIcon( new java.net.URL(myURL)); } 
      catch (java.net.MalformedURLException e) { }
    return null; }
  // ---------------------------------------------------------------------------
  public static boolean launchDefaultApplication(File file) {
    try {
      desktop = Desktop.getDesktop();
      desktop.open(file);
      return true; }
    catch (IOException ioe) { return false; } 
  }
  // ---------------------------------------------------------------------------
  public static boolean displayLink(String link) {
    desktop = Desktop.getDesktop();
    try {
      URI uri = new URI(link);
      desktop.browse(uri); 
      return true; }
    catch (Exception e) {return false; }
  }
  // ---------------------------------------------------------------------------
   public static String getPassword(int maxPasswordCharacters) {
    String paswd="";
    Random r = new Random(Calendar.getInstance().getTimeInMillis());
    for (int i=0; i<maxPasswordCharacters; i++) {
      int j = 40+r.nextInt(87);
      if (j != 96) paswd += (char)j; }
    return paswd;
    }
  //----------------------------------------------------------------------------
 public static int executeWindowsCommand(String command) {
    Runtime rt = Runtime.getRuntime();
    try {
      Process p = rt.exec(command);
      p.waitFor();
      return p.exitValue(); }
    catch (IOException e) { return -1;}
    catch (InterruptedException e) { return -1; }   
    //Process p = rt.exec("cmd.exe /C copy "+file.getAbsolutePath()+" TuristTempFile.ent.Z"); p.waitFor();
    //p = rt.exec("cmd.exe /C del /Q TuristTempFile.ent"); p.waitFor(); 
    //p = rt.exec("gzip -d TuristTempFile.ent.Z"); p.waitFor(); System.out.println(p.exitValue());          
    }
// --------------------------------------------------------------------------  
  public static boolean createDir(String filename) {
    return new File(filename).mkdir(); }
  //----------------------------------------------------------------------------
   public static boolean deleteDir(String direcoryName) {
     File dir = new File(direcoryName);
     return deleteDir(dir); }
  //----------------------------------------------------------------------------
   public static boolean deleteDir(File dir) {
     if (dir.isDirectory()) {
       String[] children = dir.list();
       for (int i=0; i<children.length; i++) {
         boolean success = deleteDir(new File(dir, children[i]));
         if (!success) return false; }
       }
     return dir.delete();
    }
  //----------------------------------------------------------------------------
  public static long getTimeStamp() {
    return Calendar.getInstance().getTimeInMillis();
  }
  //----------------------------------------------------------------------------
  public static int getBigInteger() {
    return Integer.MAX_VALUE;
  }
  // --------------------------------------------------------------------------          
  public static int getSmallInteger() {
    return Integer.MIN_VALUE;
  }
  // --------------------------------------------------------------------------          
  public static double getBigDouble() {
    return Double.MAX_VALUE;
  }
  // --------------------------------------------------------------------------          
  public static double getSmallDouble() {
    return Double.MIN_VALUE;
  }
  // --------------------------------------------------------------------------          
  public static boolean getText(String message) {
    returnText = JOptionPane.showInputDialog(message);
    if (returnText == null) return false;
    else return true;
  }
  // ---------------------------------------------------------------------------
  public static boolean getText(String message, String defaultValue) {
    returnText = JOptionPane.showInputDialog(message,defaultValue);
    if (returnText == null) return false;
    else return true;
  }
  // ---------------------------------------------------------------------------
  public static boolean getDouble(String message, double defaultValue) {
    returnText = JOptionPane.showInputDialog(message,defaultValue);
    if (returnText != null) { 
      try { returnDouble = new Double(returnText).doubleValue(); }
      catch (NumberFormatException e) {
        inform("Not an acceptable value");
        return false; }
      return true;
      }
    else return false;
  }
  // ---------------------------------------------------------------------------
  public static boolean getDouble(String message, String warning) {
    returnText = JOptionPane.showInputDialog(message);
    if (returnText != null) {
      try { returnDouble = new Double(returnText).doubleValue(); }
      catch (NumberFormatException e) {
        inform(warning);
        return false; }
      return true;
      }
    else return false;
  }
  // ---------------------------------------------------------------------------
  public static boolean getDouble(double RangeStart, double RangeEnd) {
    returnText = JOptionPane.showInputDialog("Insert a real number between "+RangeStart+" and "+RangeEnd);
    if (returnText != null) {
      try { 
        returnDouble = new Double(returnText).doubleValue(); 
        if (RangeStart < RangeEnd)
          if (returnDouble >= RangeStart && returnDouble <= RangeEnd) return true;
          else {
            inform("Value outside allowable range");
            return false; }
        }
      catch (NumberFormatException e) {
        inform("Incorect floating point value");
        return false; }
      return true;
      }
    else return false;
  }
  // ---------------------------------------------------------------------------
  public static boolean getInt() {
    returnText = JOptionPane.showInputDialog("Insert an integer value");
    if (returnText != null) {
      try { 
        returnDouble = new Integer(returnText).intValue(); 
        returnInt = (int)returnDouble; }
      catch (NumberFormatException e) {
        inform("Incorect integer value");
        return false; }
      return true;
      }
    else return false;
  } 
  // ---------------------------------------------------------------------------
  public static boolean getInt(String message, int defaultValue) {
    return getInt(message, new Integer(defaultValue).toString());
  }
  // ---------------------------------------------------------------------------
  public static boolean getInt(String message, String defaultValue) {
    returnText = JOptionPane.showInputDialog(message,defaultValue);
    if (returnText != null) {
      try { 
        returnDouble = new Integer(returnText).intValue(); 
        returnInt = (int)returnDouble; }
      catch (NumberFormatException e) {
        inform("Incorect integer value");
        return false; }
      return true;
      }
    else return false;
  } 
  // ---------------------------------------------------------------------------
  public static boolean getInt(int RangeStart, int RangeEnd) {
    returnText = JOptionPane.showInputDialog("Insert an integer value between "+RangeStart+" and "+RangeEnd);
    if (returnText != null) {
      try { 
        returnDouble = new Integer(returnText).intValue(); 
        returnInt = (int)returnDouble;
        if (RangeStart < RangeEnd)
          if (returnDouble >= RangeStart && returnDouble <= RangeEnd) return true;
          else {
            inform("Value outside allowable range");
            return false; }
        } 
      catch (NumberFormatException e) {
        inform("Incorect integer value");
        return false; }
      return true;
      }
    else return false;
  }
  // ---------------------------------------------------------------------------
  public static boolean getInt(Component component, int RangeStart, int RangeEnd, String message) {
    returnText = JOptionPane.showInputDialog(component,"Insert an integer value between "+RangeStart+" and "+RangeEnd,message,JOptionPane.QUESTION_MESSAGE);
    if (returnText != null) {
      try { 
        returnDouble = new Integer(returnText).intValue(); 
        returnInt = (int)returnDouble;
        if (RangeStart < RangeEnd)
          if (returnDouble >= RangeStart && returnDouble <= RangeEnd) return true;
          else {
            inform("Value outside allowable range");
            return false; }
        } 
      catch (NumberFormatException e) {
        inform("Incorect integer value");
        return false; }
      return true;
      }
    else return false;
  }
  // ---------------------------------------------------------------------------
  public static boolean getColor(Container con, Color Default) {
    final JColorChooser c = new JColorChooser(Default);    
    c.setPreviewPanel(new JPanel());
    returnColor = c.showDialog(con,"Pick a Color",Default);
    if (returnColor == null) return false;
    else return true;
  }
  // ---------------------------------------------------------------------------
  public static java.util.Date getDate(String myDate) {
    SimpleDateFormat format = new SimpleDateFormat( "dd.MM.yyyy" );
    try {
      java.util.Date date = format.parse(myDate);
      return date;
      }
    catch (java.text.ParseException pe) {
      return null;
    }
  }
  // ---------------------------------------------------------------------------
  public static void inform(String message) {
    JFrame frame = new JFrame("Warning Message");
    JOptionPane.showMessageDialog(frame,message);
  }
  // ---------------------------------------------------------------------------
  public static void inform(String title, String message) {
    JFrame frame = new JFrame(title);
    JOptionPane.showMessageDialog(frame,message);
  }
  // ---------------------------------------------------------------------------  
  public static boolean yesQuestion(String message) {
    JFrame frame = new JFrame("Select Your Option");
    int n = JOptionPane.showConfirmDialog(frame,message,"Select your Option",JOptionPane.YES_NO_OPTION);
    if (n == JOptionPane.YES_OPTION) return true;
    else return false;
    }
  // ---------------------------------------------------------------------------
  public static String zip(String[] inputFilePath, String outputFilePath) {
    try {
      File zipFile = new File(outputFilePath);
      if (zipFile.exists()) return "Zip file already exists, please try another"; 
      FileOutputStream fos = new FileOutputStream(zipFile);
      ZipOutputStream zos = new ZipOutputStream(fos);
      int bytesRead;
      byte[] buffer = new byte[1024];
      CRC32 crc = new CRC32();
      for (int i=1, n=inputFilePath.length; i < n; i++) {
        String name = inputFilePath[i];
        File file = new File(name);
        if (!file.exists()) return "Missing file: " + name;
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        crc.reset();
        while ((bytesRead = bis.read(buffer)) != -1) crc.update(buffer, 0, bytesRead);
        bis.close();
        // Reset to beginning of input stream
        bis = new BufferedInputStream(new FileInputStream(file));
        ZipEntry entry = new ZipEntry(name);
        entry.setMethod(ZipEntry.STORED);
        entry.setCompressedSize(file.length());
        entry.setSize(file.length());
        entry.setCrc(crc.getValue());
        zos.putNextEntry(entry);
        while ((bytesRead = bis.read(buffer)) != -1) zos.write(buffer, 0, bytesRead);
        bis.close();
        }
      zos.close();
      return OK; }
    catch (java.io.IOException e) { return "IOException"; }
  }
  // ---------------------------------------------------------------------------
  public static boolean unzip(String inputFilePath, String outputFilePath) {
    try {
      //Unzip in a temporary location
      ZipInputStream  zis = new ZipInputStream (new FileInputStream(inputFilePath));
        
      int BUFFER = 2048;
      ZipEntry entry;
      while((entry = zis.getNextEntry()) != null) {
        int count;
        byte data[] = new byte[BUFFER];
        // write the files to the disk
        FileOutputStream fos = new  FileOutputStream(outputFilePath);
        BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
        //System.out.println(dest);
        while ((count = zis.read(data, 0, BUFFER)) != -1) dest.write(data, 0, count);
        dest.flush();
        dest.close();
        fos.close();
        }
      zis.close();
      return true; }
    catch (java.io.IOException e) { 
      e.printStackTrace(); 
      return false; }
  }
  // ---------------------------------------------------------------------------
  public static boolean gUnzip(String inputFilePath, String outputFilePath) {
    try {
      //Unzip in a temporary location
      GZIPInputStream zin = new GZIPInputStream(new FileInputStream(inputFilePath));
        
      int BUFFER = 2048;
      FileOutputStream fos = new FileOutputStream(outputFilePath);
      BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
      byte[] buf = new byte[1024];
      int len;
      while ((len = zin.read(buf)) > 0) dest.write(buf, 0, len);
      dest.flush();
      dest.close();
        
      zin.close(); 
      return true; }
    catch (java.io.IOException e) { 
      e.printStackTrace(); return false; }
  }
  // ---------------------------------------------------------------------------
  public static String getFileLastName(Component comp) {
    final JFileChooser f = new JFileChooser();
    int r = f.showOpenDialog(comp);
    if (r == JFileChooser.APPROVE_OPTION) {
      File ff= f.getSelectedFile();
      return ff.getName(); }
    else return null;
  }
  //----------------------------------------------------------------------------
  public static String getFilePositionFromClass(String filePath) {
    String fileName = "";
    File file = new File (".");
    //Eliminate starting drive character if any
    if (filePath.indexOf(file.separatorChar) > -1) filePath = filePath.substring(filePath.indexOf(file.separatorChar)+1);
    else return null;
    for (int i=0; i<20; i++) fileName = fileName+".."+file.separatorChar;
    return fileName+filePath;
  }
  //----------------------------------------------------------------------------
  public static String getFileName(Component comp) {
    final JFileChooser f = new JFileChooser();
    int r = f.showOpenDialog(comp);
    if (r == JFileChooser.APPROVE_OPTION) {
      File ff= f.getSelectedFile();
      return ff.getPath(); }
    else return null;
  }
  //----------------------------------------------------------------------------
  public static String getFileName(Component comp, String message) {
    final JFileChooser f = new JFileChooser();
    int r = f.showDialog(comp,message);
    if (r == JFileChooser.APPROVE_OPTION) {
      File ff= f.getSelectedFile();
      return ff.getPath(); }
    else return null;
  }
  //----------------------------------------------------------------------------
  public static String getFileNameExtension(String name) {
  try { return name.substring(name.lastIndexOf('.')+1); }
  catch (IndexOutOfBoundsException e) { return null; }
  }
  //----------------------------------------------------------------------------
  public static File getFile(Component comp) {
    final JFileChooser f = new JFileChooser();
    int r = f.showOpenDialog(comp);
    if (r == JFileChooser.APPROVE_OPTION) return f.getSelectedFile();
    else return null;
  }
  //----------------------------------------------------------------------------
  public static File getFile(Component comp, String message) {
    final JFileChooser f = new JFileChooser();
    int r = f.showDialog(comp,message);
    if (r == JFileChooser.APPROVE_OPTION) return f.getSelectedFile();
    else return null;
  }
  //----------------------------------------------------------------------------
  public static File getDirectoryFile(Component comp) {
    final JFileChooser f = new JFileChooser();
    f.setFileSelectionMode(f.DIRECTORIES_ONLY);
    int r = f.showDialog(comp, "Select");
    if (r == JFileChooser.APPROVE_OPTION) return f.getSelectedFile();
    else return null;
  } 
  //----------------------------------------------------------------------------
  public static File getDirectoryFile(Component comp, String message) {
    final JFileChooser f = new JFileChooser();
    f.setFileSelectionMode(f.DIRECTORIES_ONLY);
    int r = f.showDialog(comp, message);
    if (r == JFileChooser.APPROVE_OPTION) return f.getSelectedFile();
    else return null;
  }
  //----------------------------------------------------------------------------
  public static String getDirectory(Component comp) {
    final JFileChooser f = new JFileChooser();
    f.setFileSelectionMode(f.DIRECTORIES_ONLY);
    int r = f.showDialog(comp, "Select");
    if (r == JFileChooser.APPROVE_OPTION) {
      File ff= f.getSelectedFile();
      returnText = ff.getName();
      returnTextArray[0] = ff.getParent();
      returnTextArray[1] = ff.separator;
      return ff.getPath(); }
    return null;
  }
  //----------------------------------------------------------------------------
  public static long getTimeDifference(java.util.Date d1, java.util.Date d2) {
    return (long)Math.abs(d1.getTime() - d2.getTime()); }
  //----------------------------------------------------------------------------
  public static int getMonthDifference(java.util.Date d1, java.util.Date d2) {
    return (int)(getTimeDifference(d1,d2)/(3600*30.5)); }
  //----------------------------------------------------------------------------
  public static long getTime() {
    return Calendar.getInstance().getTimeInMillis(); }
  //----------------------------------------------------------------------------
  public static java.util.Date getToday() {
    return Calendar.getInstance().getTime(); }
  //----------------------------------------------------------------------------
  public static String getDateString() {
    String s;
    java.util.Date today = getToday();
    s = getMonth(today)+"/"+getDate(today)+"/"+getYear(today)+" - "+getHour(today)+":"+getMinute(today);
    return s; }
  //----------------------------------------------------------------------------
	public static int getDate(java.util.Date d) {
		Calendar scalendar = new GregorianCalendar();
		scalendar.setTime(d);
		return scalendar.get(Calendar.DAY_OF_MONTH); }
  //----------------------------------------------------------------------------
	public static int getYear(java.util.Date d) {
		Calendar scalendar = new GregorianCalendar();
		scalendar.setTime(d);
		return scalendar.get(Calendar.YEAR); }
  //---------------------------------------------------------------------------- 
	public static int getMonth(java.util.Date d) {
		Calendar scalendar = new GregorianCalendar();
		scalendar.setTime(d);
		return scalendar.get(Calendar.MONTH); }
  //----------------------------------------------------------------------------
  public static long getHour(java.util.Date d) {
		Calendar scalendar = new GregorianCalendar();
		scalendar.setTime(d);
    return scalendar.get(Calendar.HOUR_OF_DAY); }
  //----------------------------------------------------------------------------
  public static long getMinute(java.util.Date d) {
		Calendar scalendar = new GregorianCalendar();
		scalendar.setTime(d);
    return scalendar.get(Calendar.MINUTE); }
  //----------------------------------------------------------------------------
  public static long getSecond(java.util.Date d) {
		Calendar scalendar = new GregorianCalendar();
		scalendar.setTime(d);
    return scalendar.get(Calendar.SECOND); }
  //----------------------------------------------------------------------------
  public static long getMilisecond(java.util.Date d) {
		Calendar scalendar = new GregorianCalendar();
		scalendar.setTime(d);
    return scalendar.get(Calendar.MILLISECOND); }
  //----------------------------------------------------------------------------
  public static boolean convertStringToInteger(String string) {
    try {
      returnInt = new Integer(string.trim()).intValue();
      return true; }
    catch (NumberFormatException e) { return false; }
    }
  //----------------------------------------------------------------------------
  public static boolean convertStringToDouble(String string) {
    try {
      returnDouble = new Double(string.trim()).doubleValue();
      return true; }
    catch (NumberFormatException e) { return false; }
    }
  //----------------------------------------------------------------------------
  public static String convertMilisecondToDateString(long miliseconds) {
    java.util.Date day = new java.util.Date(miliseconds);
    return getMonth(day)+"/"+getDate(day)+"/"+getYear(day)+" - "+getHour(day)+":"+getMinute(day); }
  //----------------------------------------------------------------------------
	public static String getEnvironmentVariable(String name) {
    return System.getProperty(name); }
  //----------------------------------------------------------------------------
  public static JMenu createStyleMenu() {
    JMenu menu = new JMenu("Format");

     JMenu jMenu_FormatFont = new JMenu("Font");
        jMenu_FormatFont.add(new StyledEditorKit.FontFamilyAction("Arial Black","Arial Black"));
        jMenu_FormatFont.add(new StyledEditorKit.FontFamilyAction("Arial Narrow","Arial Narrow"));
        jMenu_FormatFont.add(new StyledEditorKit.FontFamilyAction("Book Antiqua","Book Antiqua"));
        jMenu_FormatFont.add(new StyledEditorKit.FontFamilyAction("Bookman Old Style","Bookman Old Style"));
        jMenu_FormatFont.add(new StyledEditorKit.FontFamilyAction("Courier New","Courier New"));
        jMenu_FormatFont.add(new StyledEditorKit.FontFamilyAction("Dialog","Dialog"));
        jMenu_FormatFont.add(new StyledEditorKit.FontFamilyAction("Monospaced","Monospaced"));
        jMenu_FormatFont.add(new StyledEditorKit.FontFamilyAction("Serif","Monospaced"));
        jMenu_FormatFont.add(new StyledEditorKit.FontFamilyAction("SansSerif","SansSerif"));
        jMenu_FormatFont.add(new StyledEditorKit.FontFamilyAction("Tahoma","Tahoma"));
        jMenu_FormatFont.add(new StyledEditorKit.FontFamilyAction("Times New Roman","Times New Roman"));
    menu.add(jMenu_FormatFont);

    JMenu jMenu_FormatFontDecoration = new JMenu("Decoration");
        Action action = new StyledEditorKit.BoldAction();
        action.putValue(Action.NAME, "Bold");
        jMenu_FormatFontDecoration.add(action);

        action = new StyledEditorKit.ItalicAction();
        action.putValue(Action.NAME, "Italic");
        jMenu_FormatFontDecoration.add(action);

        action = new StyledEditorKit.UnderlineAction();
        action.putValue(Action.NAME, "Underline");
        jMenu_FormatFontDecoration.add(action);
    menu.add(jMenu_FormatFontDecoration);

    JMenu jMenu_FormatFontSize = new JMenu("Size");
        jMenu_FormatFontSize.add(new StyledEditorKit.FontSizeAction("8", 8));
        jMenu_FormatFontSize.add(new StyledEditorKit.FontSizeAction("10", 10));
        jMenu_FormatFontSize.add(new StyledEditorKit.FontSizeAction("12", 12));
        jMenu_FormatFontSize.add(new StyledEditorKit.FontSizeAction("14", 14));
        jMenu_FormatFontSize.add(new StyledEditorKit.FontSizeAction("18", 18));
        jMenu_FormatFontSize.add(new StyledEditorKit.FontSizeAction("20", 20));
        jMenu_FormatFontSize.add(new StyledEditorKit.FontSizeAction("22", 22));
        jMenu_FormatFontSize.add(new StyledEditorKit.FontSizeAction("24", 24));
    menu.add(jMenu_FormatFontSize);

    JMenu jMenu_FormatFontColor = new JMenu("Color");
        jMenu_FormatFontColor.add(new StyledEditorKit.ForegroundAction("Red",Color.red));
        jMenu_FormatFontColor.add(new StyledEditorKit.ForegroundAction("Green",Color.green));
        jMenu_FormatFontColor.add(new StyledEditorKit.ForegroundAction("Blue",Color.blue));
        jMenu_FormatFontColor.add(new StyledEditorKit.ForegroundAction("Black",Color.black));
    menu.add(jMenu_FormatFontColor);

    return menu;
    }
  //----------------------------------------------------------------------------
  public static boolean sendEMail(String mailServer, String fromAddress, String toAddress, String subjectText, String messageText) {
    Properties props=new Properties();
    props.put("mail.smtp.host",mailServer);
    Session session=Session.getDefaultInstance(props,null);
    session.setDebug(false);

    try{
      // Define message
      MimeMessage message=new MimeMessage(session);
      message.setFrom(new InternetAddress(fromAddress));
      message.addRecipient(Message.RecipientType.TO,new InternetAddress(toAddress));
      message.setSubject(subjectText);
      message.setSentDate(getToday());

      // create the message part 
      MimeBodyPart messageBodyPart = new MimeBodyPart();
      messageBodyPart.setText(messageText);
      Multipart multipart = new MimeMultipart();
      multipart.addBodyPart(messageBodyPart);
      message.setContent(multipart);

      // Send the message
      Transport.send(message); }
    catch (MessagingException mex) {
      inform("Couldn't send e-mail");
      return false;
      }
    return true;
	}
  //----------------------------------------------------------------------------
  public static boolean sendEMail(String mailServer, String port, String username, String password, String fromAddress, String toAddress, String subjectText, String messageText) {
    Properties props = System.getProperties();
    props.put("mail.transport.protocol", "SMTP");
    props.put("mail.host", mailServer);
    props.put("mail.user", username);
    props.put("mail.smtp.host", mailServer);
    props.put("mail.smtp.user", username);
    props.put("mail.smtp.port", port);
    props.put("mail.from", fromAddress);
    props.put("mail.auth", "true");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    props.put("mail.smtp.socketFactory.fallback", "false");
    props.put("mail.smtp.socketFactory.port", new Integer(port).toString());
    System.setProperties(props);

    SMTPAuthenticator auth = new SMTPAuthenticator();
    auth.setUsername(username);
    auth.setPassword(password);
    Session sess = Session.getDefaultInstance(props, auth);
    sess.setDebug(true);

    try {
      URLName urln = new URLName("smtp", mailServer, new Integer(port).intValue(), "", username, password);
      SMTPMessage email = new SMTPMessage(sess);

      email.setReplyTo(new Address[]{new InternetAddress(toAddress)});
      email.setSubject(subjectText);
      email.setText(messageText);
      email.setSentDate(new java.util.Date());
      email.setFrom(new InternetAddress(fromAddress));
      email.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
      email.saveChanges();

      SMTPSSLTransport strans = new SMTPSSLTransport(sess, urln);

      //strans.setLocalHost("localhost");
      strans.connect(mailServer, new Integer(port).intValue(), username, password);
      strans.sendMessage(email, email.getAllRecipients());
      strans.close();
      }
    catch (MessagingException me)        {
      inform(me.toString());//inform("Couldn't send e-mail");
      return false; }
    return true;
    }
  public static class SMTPAuthenticator extends javax.mail.Authenticator {
    private String username;
    private String password;

    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password); }

    public String getUsername() {
        return username; }

    public void setUsername(String username) {
        this.username = username; }

    public String getPassword() {
        return password;}

    public void setPassword(String password) {
        this.password = password; }
    }
  // ---------------------------------------------------------------------------
  public static boolean ftpDownload(Component root, String server, String username, String password, String remoteFolder) {
    File file = getDirectoryFile(root, "Select PDB directory");
    if (file != null)
    try {
      String myPDBDirectory = file.getAbsolutePath();

      //Connect to pdb server
      FTPClient ftp = new FTPClient();
      ftp.connect(server);
      ftp.login(username,password);
      ftp.changeWorkingDirectory(remoteFolder);
      //Get list of folders
      FTPFile[] pdbDirectoryList = ftp.listFiles();
      for ( int iPDBDir=0; iPDBDir<pdbDirectoryList.length; iPDBDir++ ) {
        String pdbDirectoryName = pdbDirectoryList[iPDBDir].getName();
        //check if it doesn't exist in my folder
        String myDir = myPDBDirectory+File.separator+pdbDirectoryName;
        if (!(new File(myDir).isDirectory())) new File(myDir).mkdir();

        ftp.changeWorkingDirectory(pdbDirectoryName);
        FTPFile[] pdbFileList = ftp.listFiles();

        for (int i=0; i<pdbFileList.length; i++) {
          String filename = pdbFileList[i].getName();
          //check if filename exists - doesn't overidde local files
          File f = new File(myDir+File.separator+filename);
          if (!f.exists()) {
            FileOutputStream fos = new FileOutputStream(f);
            ftp.retrieveFile(filename, fos );
            fos.close();
            }

          }
        }//for( int iPDBDir
      // Logout from the FTP Server and disconnect
      ftp.logout();
      ftp.disconnect();
      }
    catch (FileNotFoundException e) { returnText = "File not found - Abording"; return false; }
    catch (IOException e) { returnText = "IO Exception - Abording"; return false; }
    return true;
  }
  //------------------------------------------------------------------------------
  private Object deserializeObject (byte[] data) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
    ByteArrayInputStream byteArrayIn = new ByteArrayInputStream (data);
    ObjectInputStream objectInput = new ObjectInputStream (byteArrayIn);
    Object object = objectInput.readObject ();
    return object;
    }
  // ---------------------------------------------------------------------------
  public void insertionSort(double [] matrix) {
    double min;

    for (int run=1; run<matrix.length; run++)
        //Locate the first element from the begining with matrix[run] < matrix[element]
        for (int element=0; element<run; element++)
            if (matrix[run] < matrix[element]) {
                //Store in temporary location
                min = matrix[run];
                //Move one position back
                for (int pos=run; pos>element; pos--)
                    matrix[pos] = matrix[pos-1];
                //Place matrix[run] now stored in min in its right place
                matrix[element] = min; }
    }
  //------------------------------------------------------------------------------
  public void selectionSort(double [] matrix) {
    double min;
    int pos=0;

    for (int run=0; run<matrix.length-1; run++) {
        //Locate minimum of remaining elements
        min = matrix[run];
        for (int element=run+1; element<matrix.length; element++)
            if (matrix[element] < min) {
                min = matrix[element];
                pos = element; }
        //If new minimum found swap positions
        if (min < matrix[run]) {
            matrix[pos] = matrix[run];
            matrix[run] = min; }
        }
   }
  //------------------------------------------------------------------------------
  public void bubbleSort(double [] matrix) {
    double min;
    //Number of bubble runs
    for (int run=1; run<matrix.length; run++)
        //Check the bubble
        for (int element=matrix.length-1; element>=run; element--)
            if (matrix[element] < matrix[element-1]) {
                //Store in temporary location
                min = matrix[element];
                matrix[element] = matrix[element-1];
                matrix[element-1] = min;
                }
  }
  //----------------------------------------------------------------------------
  public static Connection getClientConnectionToDBMS(Connection conn, String clientDBDriver, String clientConnectionName) {
    conn = null;
    try {
      Class.forName(clientDBDriver).newInstance();
      conn = DriverManager.getConnection(clientConnectionName);
      }
    catch(ClassNotFoundException ce){           inform("Connection Error: Database driver not found\n");  ce.printStackTrace();}
    catch(java.lang.IllegalAccessException ce){ inform("Connection Error: IllegalAccessException\n");  ce.printStackTrace();}
    catch(java.lang.InstantiationException ce){ inform("Connection Error: Database driver anable to instantiate\n");  ce.printStackTrace();}
    catch(SQLException ce){                     inform("Connection Error: SQL Exception connect: "+ce.getMessage()+"\n");  ce.printStackTrace();}
    return conn;
    }
  // ---------------------------------------------------------------------------
  public static boolean disconnectToDBMS(Connection conn) {
    try {
      conn.close();
      return true; }
    catch(SQLException ce){
      inform("Dissconnect Error: SQL Exception: "+ce.getMessage()+"\n");
      return false; }
    }
  //----------------------------------------------------------------------------
  public static boolean exportTextToFile(Component parent, String text) {
    String fileLocation = getFileName(parent);
    if (fileLocation != null)
    try {
      DataOutputStream out = new DataOutputStream(new FileOutputStream(fileLocation));
      out.writeBytes(text);
      out.close();
      return true; }
    catch (FileNotFoundException e) {inform("File not Found - Check permitions"); }
    catch (IOException e) {inform("Unspecified error - Contact administrator");}
    return false;
  }
  //----------------------------------------------------------------------------
  public static boolean searchTables(Connection conn, String searchString) {
    try {
      if (conn != null) {
        CallableStatement call = conn.prepareCall("{CALL Search(?,?)}");
        call.registerOutParameter(2, Types.VARCHAR);
        call.setString(1,searchString);
        call.execute();
        returnText = call.getString(2);
        return true;
        }// if (conn != null
      else inform("Could not establish connection with server - Contact administrator");
      }// try
    catch(SQLException ce){ inform("Error performing search - Contact administrator\n"); ce.printStackTrace(); }
    return false;
  }
  //----------------------------------------------------------------------------
}// End of class Utilities
