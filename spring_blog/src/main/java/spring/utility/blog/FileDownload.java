package spring.utility.blog;
 
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
 
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class FileDownload extends HttpServlet {
  // ?λΈλ¦Ώ λ²μ , ??΅ κ°??₯
  static final long serialVersionUID = 1L;
 
  // web.xml? ? κ·? κ°??₯
  private ServletConfig config = null;
 
  // κΈ°λ³Έ ??±?
  public FileDownload() {
    super();
  }
 
  /**
   * ?°μΊ? ?€?? μ΅μ΄ 1λ²λ§ ?€? ?©??€. init()?? ??΅κ°??₯
   * 
   * @param config
   *          web.xml? ? κ·Όκ??₯, ?λΈλ¦Ώ ?κ²? ? λ³? ???₯ κ°μ²΄
   */
  @Override
  public void init(ServletConfig config) throws ServletException {
    this.config = config;
  }
 
  // Form - get λ°©μ μ²λ¦¬
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doProcess(request, response);
  }
 
  // Form - post λ°©μ μ²λ¦¬
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doProcess(request, response);
  }
 
  protected void doProcess(HttpServletRequest request,
    HttpServletResponse response) throws ServletException, IOException {
    ServletContext ctx = config.getServletContext();
 
    // ???₯ ?΄?λ₯? ? ?? κ²½λ‘λ‘? λ³??
    String dir = ctx.getRealPath(request.getParameter("dir"));
 
    // ??Όλͺ? λ°κΈ°
    String filename = request.getParameter("filename");
 
    // ? μ²? ? ?? κ²½λ‘ μ‘°ν©
    File file = new File(dir + "/" + filename);
    String fileStr = dir + "/" + filename;
    String contentType = getType(fileStr);
    System.out.println("?€?΄λ‘λ ???: " + contentType);
 
    String disposition = getDisposition(filename, getBrowser(request));
    response.addHeader("Content-disposition", disposition);
    response.setHeader("Content-Transfer-Encoding", "binary");
    response.setContentLength((int) file.length());
    response.setContentType(contentType);
    response.setHeader("Connection", "close");
 
    // ??Ό? ?½?΄?¬ ?¬κΈ? μ§?? 
    byte buffer[] = new byte[4096];
 
    try {
      if (file.isFile()) {
        // ??Ό? ?½?΄ ?€? ?­?
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
            file));
 
        // ?¬?΄?Έ? ? ?? ? ???κ²? μΆλ ₯
        BufferedOutputStream bos = new BufferedOutputStream(
            response.getOutputStream());
 
        int read = 0;
        // bis.read(buffer): ??Ό?? 4KB?© ?½?΄?
        // buffer? ???₯
        // ??Ό? ??΄λ©? -1
        while ((read = bis.read(buffer)) != -1) {
          // ? ??λ‘? μΆλ ₯
          bos.write(buffer, 0, read);
        }// while
        bis.close();
        bos.close();
      }
 
    } catch (Exception e) {
 
    }
  }
 
  public String getType(String fileUrl) {
    String type = "";
    fileUrl = "file:" + fileUrl;
    try {
      URL u = new URL(fileUrl);
      URLConnection uc = u.openConnection();
      type = uc.getContentType();
 
    } catch (Exception e) {
      System.out.println(e.toString());
    }
 
    return type;
  }
 
  public String getBrowser(HttpServletRequest request) {
    String header = request.getHeader("User-Agent");
    if (header.indexOf("MSIE") > -1) {
      return "MSIE";
    } else if (header.indexOf("Chrome") > -1) {
      return "Chrome";
    } else if (header.indexOf("Opera") > -1) {
      return "Opera";
    }
    return "Firefox";
  }
 
  public static synchronized String getDisposition(String filename, String browser) {
    String dispositionPrefix = "attachment;filename=";
    String encodedFilename = null;
    try {
      if (browser.equals("MSIE")) {
        encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll(
            "\\+", "%20");
      } else if (browser.equals("Firefox")) {
        encodedFilename =
 
        "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
      } else if (browser.equals("Opera")) {
        encodedFilename =
 
        "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
      } else if (browser.equals("Chrome")) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < filename.length(); i++) {
          char c = filename.charAt(i);
          if (c > '~') {
            sb.append(URLEncoder.encode("" + c, "UTF-8"));
          } else {
            sb.append(c);
          }
        }
        encodedFilename = sb.toString();
      } else {
        System.out.println("Not supported browser");
      }
    } catch (Exception e) {
 
    }
 
    return dispositionPrefix + encodedFilename;
  }
 
}