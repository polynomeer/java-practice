package playground;

import java.io.*;

public class JsonToSql {
    public static String itemToSql(String s) {
        return s.replace("'새벽배송','전국택배'", "'[\"새벽배송\",\"전국택배\"]'")
                .replace("badge0", "badge")
                .replace("delivery_type0,delivery_type1", "delivery_type")
                .replace("n_price", "normal_price")
                .replace("s_price", "sale_price")
                .replace("detail_hash", "category, detail_hash")
                .replace("VALUES (", "VALUES (17011200,")
                .replace("section)", "section,stock)")
                .replace(");", ",10);");
    }

    public static String detailJson(String s) {
        return s.replace("{\"hash\":", "{\"detail_hash\":")
                .replace("\"data\":{", "")
                .replace("]}}", "]}");
    }

    public static String detailItemJson(String s) {
        return s.replace("\"", "\\\"")
                .replace("[", "\"[")
                .replace("]", "]\"");
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("./input.txt"));
        PrintWriter pw = new PrintWriter("./output.txt");
        while (true) {
            String line = br.readLine();
            if (line == null) break;
            String result = itemToSql(line);
//            String result = detailJson(line);
//            String result = detailItemJson(line);
            System.out.println(result);
            pw.println(result);
        }
        br.close();
        pw.close();

    }
}
