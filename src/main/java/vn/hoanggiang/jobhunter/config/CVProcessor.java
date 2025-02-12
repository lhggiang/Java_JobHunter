package vn.hoanggiang.jobhunter.config;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class CVProcessor {
        public static String extractText(File file) throws Exception {
            // Tải tệp PDF vào bộ nhớ
            PDDocument document = Loader.loadPDF(file);

            // Tạo đối tượng để trích xuất văn bản từ PDF
            PDFTextStripper pdfStripper = new PDFTextStripper();

            // Lấy nội dung văn bản từ PDF
            String text = pdfStripper.getText(document);

            // Đóng tài liệu để giải phóng tài nguyên
            document.close();

            // Trả về nội dung văn bản trích xuất được
            return text;
    }
}
