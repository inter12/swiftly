package config.spring.test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-11-18
 *  Time: 下午1:47
 * 
 * </pre>
 */
public class MainTest {

    public static void main(String[] args) throws Exception {

        Map<String, Info> map = new HashMap<String, Info>(600);

        File readFile1 = new File("/tmp/vdper/22/c21");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(readFile1)));

        File readFile2 = new File("/tmp/vdper/22/c11");
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(new FileInputStream(readFile2)));

        String temp = null;
        while ((temp = reader.readLine()) != null) {
            String[] split = temp.split("\t");

            String userId = split[0].trim();
            String count = split[1].trim();

            Info info = new MainTest.Info(userId, count);
            map.put(userId, info);

        }

        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream("/tmp/vdper/22/result")));

        String temp2 = null;
        while ((temp2 = reader2.readLine()) != null) {
            String[] split = temp2.split("\t");
            String userId = split[0].trim();
            String newCount = split[1].trim();
            String oldCount = "0";

            if (map.containsKey(userId)) {
                oldCount = map.get(userId).getCount();
            }
            printWriter.write(split[0] + " " + oldCount + " " + newCount);
            printWriter.write("\n");
        }

        reader.close();
        reader2.close();
        printWriter.close();

    }

    static class Info {

        private String userId;

        private String count;

        public Info(String userId, String count) {
            this.userId = userId;
            this.count = count;
        }

        String getUserId() {
            return userId;
        }

        void setUserId(String userId) {
            this.userId = userId;
        }

        String getCount() {
            return count;
        }

        void setCount(String count) {
            this.count = count;
        }
    }
}
