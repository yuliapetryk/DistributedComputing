package TaskA;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Controller {
    private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    static File database = new File("data.txt");

    public static void getPhoneNumbers(String user) {
        try {
            readWriteLock.readLock().lock();
            List<String> phoneNumbers = new ArrayList<>();
            BufferedReader file = new BufferedReader(new FileReader(database));
            String line = file.readLine();
            while (line != null) {
                String[] parts = line.split(" ", 2);
                if (parts.length > 1 && parts[0].equals(user)) {
                    phoneNumbers.add(parts[1]);
                }
                line = file.readLine();
            }
            System.out.println("User " + user + " uses this phone numbers: " + phoneNumbers);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);

        } catch (IOException e) {
            throw new RuntimeException(e);

        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public static void getNames(String phoneNumber) {
        try {
            readWriteLock.readLock().lock();
            List<String> names = new ArrayList<>();
            BufferedReader file = new BufferedReader(new FileReader(database));
            String line = file.readLine();
            while (line != null) {
                String[] parts = line.split(" ", 2);
                if (parts.length > 1 && parts[1].equals(phoneNumber)) {
                    names.add(parts[0]);
                }
                line = file.readLine();
            }
            System.out.println("Phone number " + phoneNumber + " is used by :" + names);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);

        } catch (IOException e) {
            throw new RuntimeException(e);

        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public void addRecord(String name, String phoneNumber) {
        PrintWriter pw = null;

        try {
            readWriteLock.writeLock().lock();
            pw = new PrintWriter(new BufferedWriter(new FileWriter(database, true)));
            pw.println(name + " " + phoneNumber);
            System.out.println("Adding: " + name + " " + phoneNumber);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);

        } catch (IOException e) {
            throw new RuntimeException(e);

        } finally {
            assert pw != null;
            pw.close();
            readWriteLock.writeLock().unlock();
        }
    }

    public static int countLines(File fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        int lineCount = 0;
        while (reader.readLine() != null) {
            lineCount++;
        }
        reader.close();
        return lineCount;
    }

    public void removeRecord() throws IOException {
        Random random = new Random();
        int startLine = random.nextInt(countLines(database));
        try {
            int numLines = 1;
            BufferedReader br = new BufferedReader(new FileReader(database));
            StringBuilder sb = new StringBuilder("");
            int linenumber = 0;
            String line;
            String removedLine = "";

            while ((line = br.readLine()) != null) {
                if (linenumber < startLine || linenumber >= startLine + numLines) {
                    sb.append(line).append("\n");
                } else {
                    removedLine = line;
                }
                linenumber++;
            }

            br.close();

            FileWriter temp = new FileWriter(database);
            temp.write(sb.toString());
            temp.close();

            System.out.println("Removing: " + removedLine);

        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }
}