package it.cgmconsulting.ms_post.service;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfDocumentInfo;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import it.cgmconsulting.ms_post.payload.response.PostDetailResponse;
import it.cgmconsulting.ms_post.payload.response.SectionResponse;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {

    public InputStream createPdf(PostDetailResponse post) throws IOException {
        String title = post.getTitle();
        String image = post.getPostImage();
        String author = post.getAuthor();
        double avg = post.getAverage();
        String keywords = String.join(", ", post.getTagNames());

        // Presentazione (1), Preparazione (2), Consigli (3), Conservazione (4)
        String title1 = null;
        String title2 = null;
        String title3 = null;
        String title4 = null;
        String subTitle1 = null;
        String subTitle2 = null;
        String subTitle3 = null;
        String subTitle4 = null;
        String image1 = null;
        String image2 = null;
        String image3 = null;
        String image4 = null;
        String content1 = null;
        String content2 = null;
        String content3 = null;
        String content4 = null;

        for (SectionResponse s : post.getSections()) {
            if (s.getSectionTitle().equalsIgnoreCase("presentazione")) {
                title1 = s.getSectionTitle();
                subTitle1 = s.getSubTitle();
                image1 = s.getSectionImage();
                content1 = s.getContent();
            } else if (s.getSectionTitle().equalsIgnoreCase("preparazione")) {
                title2 = s.getSectionTitle();
                subTitle2 = s.getSubTitle();
                image2 = s.getSectionImage();
                content2 = s.getContent();
            } else if (s.getSectionTitle().equalsIgnoreCase("consigli")) {
                title3 = s.getSectionTitle();
                subTitle3 = s.getSubTitle();
                image3 = s.getSectionImage();
                content3 = s.getContent();
            } else {
                title4 = s.getSectionTitle();
                subTitle4 = s.getSubTitle();
                image4 = s.getSectionImage();
                content4 = s.getContent();
            }
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(out));
        Document document = new Document(pdfDocument, PageSize.A4);

        addMetaData(pdfDocument, title, author, keywords);

        // TITLE
        Paragraph pTitle = new Paragraph(title).setFontSize(20).setBold().setFontColor(new DeviceRgb(220, 15, 158), 100);
        document.add(pTitle);

        if (image != null) {
            ImageData imageData = ImageDataFactory.create(image);
            document.add(new Image(imageData));
        }

        // Presentazione
        Paragraph pSectionTitle1 = new Paragraph(title1);
        document.add(pSectionTitle1);
        if (subTitle1 != null) {
            Paragraph pSubTitle1 = new Paragraph(subTitle1);
            document.add(pSubTitle1);
        }
        if (image1 != null) {
            ImageData imageData = ImageDataFactory.create(image1);
            document.add(new Image(imageData).setWidth(100).setHeight(100));
        }
        Paragraph pContent1 = new Paragraph(content1);
        document.add(pContent1);

        // Preparazione
        Paragraph pSectionTitle2 = new Paragraph(title2);
        document.add(pSectionTitle2);
        if (subTitle2 != null) {
            Paragraph pSubTitle2 = new Paragraph(subTitle2);
            document.add(pSubTitle2);
        }
        if (image2 != null) {
            ImageData imageData = ImageDataFactory.create(image2);
            document.add(new Image(imageData).setWidth(100).setHeight(100));
        }
        Paragraph pContent2 = new Paragraph(content2);
        document.add(pContent2);

        // Consigli
        if (title3 != null) {
            Paragraph pSectionTitle3 = new Paragraph(title3);
            document.add(pSectionTitle3);
            if (subTitle3 != null) {
                Paragraph pSubTitle3 = new Paragraph(subTitle3);
                document.add(pSubTitle3);
            }
            if (image3 != null) {
                ImageData imageData = ImageDataFactory.create(image3);
                document.add(new Image(imageData).setWidth(100).setHeight(100));
            }
            Paragraph pContent3 = new Paragraph(content3);
            document.add(pContent3);
        }

        // Conservazione
        if (title4 != null) {
            Paragraph pSectionTitle4 = new Paragraph(title4);
            document.add(pSectionTitle4);
            if (subTitle4 != null) {
                Paragraph pSubTitle4 = new Paragraph(subTitle4);
                document.add(pSubTitle4);
            }
            if (image4 != null) {
                ImageData imageData = ImageDataFactory.create(image4);
                document.add(new Image(imageData).setWidth(100).setHeight(100));
            }
            Paragraph pContent4 = new Paragraph(content4);
            document.add(pContent4);
        }

        Paragraph pAuthor = new Paragraph(author != null ? "Author: " + author : "Author: N/A");
        document.add(pAuthor);

        Paragraph pAvg = new Paragraph(avg != 0 ? "Rating: " + avg : "Rating: N/A");
        document.add(pAvg);

        Paragraph pDate = new Paragraph("Published on " + post.getPublicationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        document.add(pDate);

        // NUMERI DI PAGINA (bottom/right)
        int numberOfPages = pdfDocument.getNumberOfPages();
        for (int i = 1; i <= numberOfPages; i++) {
            document.showTextAligned(new Paragraph(String.format("page %s of %s", i, numberOfPages)).setFontSize(8).setItalic(),
                    560, 20, i, TextAlignment.RIGHT, VerticalAlignment.BOTTOM, 0);
        }

        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    }

    private void addMetaData(PdfDocument pdf, String title, String author, String keywords) {
        PdfDocumentInfo info = pdf.getDocumentInfo();
        info.setTitle(title);
        info.setAuthor(author);
        info.setKeywords(keywords);
    }
}
