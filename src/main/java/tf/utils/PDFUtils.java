package tf.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class PDFUtils {

	

    /**
     * PDF合并
     * @param bytesList
     * @param toPath
     * @return
     */
    public byte[] mergePDF(List<byte[]> bytesList) {

    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
		try {
				  PdfCopy copy= new PdfCopy(document, baos);
				 document.open();
		            for (byte[] pdfByte : bytesList) {
		                PdfReader reader = new PdfReader(pdfByte);
		                copy.addDocument(reader);
		                copy.freeReader(reader);
		                reader.close();
		            }
		            document.close();	
			} catch (DocumentException | IOException e) {
				e.printStackTrace();
			}
        return baos.toByteArray();
    }
    public void mergePDF(List<byte[]> bytesList,String toPath)  {

    	 OutputStream out = null;
    	 try {
    		 out = new FileOutputStream( toPath);
			out.write(mergePDF(bytesList));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
       
    }

    
    public  byte[] html2PDF(String content) throws Exception {
        // step 2
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        
        renderer.setDocumentFromString(content);

        //中文支持
        ITextFontResolver fontResolver = renderer.getFontResolver();
        if("linux".equals(getCurrentOperatingSystem())){
            fontResolver.addFont("/usr/share/fonts/chiness/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        }else{
            fontResolver.addFont("c:/Windows/Fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        }

        
        renderer.layout();
        renderer.createPDF(baos);
        baos.close();
        return  baos.toByteArray();

    }

    public static String getCurrentOperatingSystem(){
        String os = System.getProperty("os.name").toLowerCase();
        return os;
    }

	
    
    
    
	
}
