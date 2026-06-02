package za.gov.dpw.iarts.service;

import org.springframework.stereotype.Service;
import za.gov.dpw.iarts.dto.PermissionRemovalDto;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionRemovalPdfService {
    private static final int LINES_PER_PAGE = 44;

    public byte[] generate(PermissionRemovalDto dto) {
        List<String> lines = formLines(dto);
        List<List<String>> pages = pages(lines);
        List<byte[]> objects = new ArrayList<>();
        int fontObjectId = 3;
        List<Integer> pageObjectIds = new ArrayList<>();
        List<Integer> contentObjectIds = new ArrayList<>();

        objects.add(pdfObject(1, "<< /Type /Catalog /Pages 2 0 R >>"));
        objects.add(new byte[0]);
        objects.add(pdfObject(fontObjectId, "<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica >>"));

        int nextObjectId = 4;
        for (List<String> pageLines : pages) {
            int pageObjectId = nextObjectId++;
            int contentObjectId = nextObjectId++;
            pageObjectIds.add(pageObjectId);
            contentObjectIds.add(contentObjectId);
            objects.add(pdfObject(pageObjectId, "<< /Type /Page /Parent 2 0 R /MediaBox [0 0 595 842] /Resources << /Font << /F1 3 0 R >> >> /Contents " + contentObjectId + " 0 R >>"));
            objects.add(pdfStreamObject(contentObjectId, pageContent(pageLines)));
        }

        StringBuilder kids = new StringBuilder();
        for (Integer pageObjectId : pageObjectIds) {
            kids.append(pageObjectId).append(" 0 R ");
        }
        objects.set(1, pdfObject(2, "<< /Type /Pages /Kids [ " + kids + "] /Count " + pageObjectIds.size() + " >>"));

        return writePdf(objects);
    }

    private List<String> formLines(PermissionRemovalDto dto) {
        List<String> lines = new ArrayList<>();
        lines.add("PERMISSION TO REMOVE EQUIPMENT FROM THE BUILDING(CGO)");
        lines.add("");
        lines.add("A. PARTICULARS OF THE OFFICIAL REMOVING EQUIPMENT");
        addField(lines, "NAME AND SURNAME OF OFFICIAL", dto.officialName());
        addField(lines, "UNIT /DIRECTORATE / BRANCH", dto.unitDirectorateBranch());
        addField(lines, "TELEPHONE NUMBER", dto.telephoneNumber());
        addField(lines, "IDENTITY OR PERSAL NUMBER", dto.identityOrPersalNumber());
        addField(lines, "REASON TO REMOVE EQUIPMENT FROM BUILDING", dto.removalReason());
        lines.add("SIGNATURE OF OFFICIAL: ________________________________");
        lines.add("");
        lines.add("C. DESCRIPTION OF EQUIPMENT");
        addField(lines, "Description", dto.equipmentDescription());
        addField(lines, "Bar code", dto.barCode());
        addField(lines, "Serial Number", dto.serialNumber());
        lines.add("");
        lines.add("D. PERMISSION REQUESTED");
        lines.add("Permission is requested to remove equipment specified above or on the list attached");
        addField(lines, "CURRENT LOCATION(ROOM NUMBER AND BUILDING)", dto.currentLocation());
        addField(lines, "PERIOD(If it's a laptop not more than 10 days, if more than 10 days motivation needed)", dto.period());
        addField(lines, "NEW LOCATION", dto.newLocation());
        lines.add("");
        lines.add("E. PERMISSION GRANTED (ICT)");
        wrap(lines, "Equipment as in C and serial number checked. Only authorised signature(P. Mphuthi, M. Repoo, J. Lupuwane, M. Mahlanga, T. Sithebe & B Silaule) allowed either on the stamp or as signature on the form. To be checked against specimen signatures.");
        addSignatureLine(lines, "Signature", "Date", dto.ictDate());
        lines.add("");
        lines.add("E. PERMISSION GRANTED (MAM)");
        wrap(lines, "Equipment as in C and serial number checked. Only authorised signature(T Mamabolo,L Ditsepo,T Khumalo,E Machoga, T.Sekgala & B Mokoana) allowed either on the stamp or as signature on the form. To be checked against specimen signatures.");
        addSignatureLine(lines, "Signature", "Date", dto.mamDate());
        lines.add("");
        lines.add("F. SECURITY CHECK AND SIGNATURE");
        wrap(lines, "Only supervisors to sign (T. Makwela, E. Tshivase, E. Makgalefa, M Sikhathi, D Mokonyama, L Phahladira, R Mudau, A Kgobane, M . Mohapi, M . Matsepe).");
        addSignatureLine(lines, "Signature", "Date", dto.securityDate());
        return lines;
    }

    private void addField(List<String> lines, String label, String value) {
        wrap(lines, label + ": " + value(value));
    }

    private void addSignatureLine(List<String> lines, String signatureLabel, String dateLabel, String date) {
        lines.add(signatureLabel + ": ________________________________    " + dateLabel + ": " + value(date));
    }

    private String value(String value) {
        return value == null || value.isBlank() ? "________________" : value;
    }

    private void wrap(List<String> lines, String text) {
        int limit = 92;
        String remaining = text == null ? "" : text.trim();
        while (remaining.length() > limit) {
            int split = remaining.lastIndexOf(' ', limit);
            if (split <= 0) {
                split = limit;
            }
            lines.add(remaining.substring(0, split).trim());
            remaining = remaining.substring(split).trim();
        }
        lines.add(remaining);
    }

    private List<List<String>> pages(List<String> lines) {
        List<List<String>> pages = new ArrayList<>();
        for (int index = 0; index < lines.size(); index += LINES_PER_PAGE) {
            pages.add(lines.subList(index, Math.min(index + LINES_PER_PAGE, lines.size())));
        }
        return pages.isEmpty() ? List.of(List.of("")) : pages;
    }

    private String pageContent(List<String> lines) {
        StringBuilder content = new StringBuilder();
        content.append("BT\n/F1 10 Tf\n50 800 Td\n14 TL\n");
        for (String line : lines) {
            content.append("(").append(escape(line)).append(") Tj\nT*\n");
        }
        content.append("ET\n");
        return content.toString();
    }

    private String escape(String value) {
        return value.replace("\\", "\\\\").replace("(", "\\(").replace(")", "\\)");
    }

    private byte[] pdfObject(int id, String body) {
        return (id + " 0 obj\n" + body + "\nendobj\n").getBytes(StandardCharsets.ISO_8859_1);
    }

    private byte[] pdfStreamObject(int id, String content) {
        byte[] streamBytes = content.getBytes(StandardCharsets.ISO_8859_1);
        String header = id + " 0 obj\n<< /Length " + streamBytes.length + " >>\nstream\n";
        String footer = "endstream\nendobj\n";
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        output.writeBytes(header.getBytes(StandardCharsets.ISO_8859_1));
        output.writeBytes(streamBytes);
        output.writeBytes(footer.getBytes(StandardCharsets.ISO_8859_1));
        return output.toByteArray();
    }

    private byte[] writePdf(List<byte[]> objects) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        List<Integer> offsets = new ArrayList<>();
        output.writeBytes("%PDF-1.4\n".getBytes(StandardCharsets.ISO_8859_1));
        for (byte[] object : objects) {
            offsets.add(output.size());
            output.writeBytes(object);
        }
        int xrefOffset = output.size();
        output.writeBytes(("xref\n0 " + (objects.size() + 1) + "\n").getBytes(StandardCharsets.ISO_8859_1));
        output.writeBytes("0000000000 65535 f \n".getBytes(StandardCharsets.ISO_8859_1));
        for (Integer offset : offsets) {
            output.writeBytes(String.format("%010d 00000 n \n", offset).getBytes(StandardCharsets.ISO_8859_1));
        }
        output.writeBytes(("trailer\n<< /Size " + (objects.size() + 1) + " /Root 1 0 R >>\nstartxref\n" + xrefOffset + "\n%%EOF\n").getBytes(StandardCharsets.ISO_8859_1));
        return output.toByteArray();
    }
}
