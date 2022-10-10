

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class IO
{
    public static boolean isFile(String path)
    {
        File f = new File(path);
        return (f.exists()) && (f.isFile());
    }

    public static boolean createDirectory(String path)
    {
        File f = new File(path);
        try
        {
            return f.mkdir();
        }
        catch (Exception e) {}
        return false;
    }

    public static boolean deleteDirectory(String path)
    {
        return deleteDirectory(new File(path));
    }

    public static boolean deleteDirectory(File dir)
    {
        if ((dir == null) || (!dir.isDirectory())) {
            return false;
        }
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteDirectory(f);
                } else if (!f.delete()) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public static boolean deleteFile(File file)
    {
        file.setWritable(true);
        return file.delete();
    }

    public static boolean renameFile(File src, File dst, int mode)
    {
        boolean success = src.renameTo(dst);
        if (success) {
            return true;
        }
        if ((mode == 0) || (!src.exists()) || (!dst.exists())) {
            return false;
        }
        if (mode == 1) {
            return (deleteFile(dst)) && (src.renameTo(dst));
        }
        if (mode == 2) {
            return deleteFile(src);
        }
        throw new IllegalArgumentException();
    }

    public static List<String> listFiles(String path)
    {
        List<String> list = new ArrayList();
        listFilesRecurse(new File(path), list);
        return list;
    }

    private static void listFilesRecurse(File base, List<String> list)
    {
        if (base.isDirectory()) {
            for (String name : base.list()) {
                listFilesRecurse(new File(base, name), list);
            }
        } else if (base.isFile()) {
            list.add(base.getAbsolutePath());
        }
    }

    public static File createTempFolder(String name)
            throws IOException
    {
        File f = File.createTempFile("jebgettemp", null);
        File folder = new File(f.getParent(), name);
        f.delete();
        folder.mkdir();
        return folder;
    }

    public static void deleteFolderOnExit(File dir)
    {
        dir.deleteOnExit();
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteFolderOnExit(f);
                } else {
                    f.deleteOnExit();
                }
            }
        }
    }

    public static void writeFile(File file, byte[] data, boolean createDirs)
            throws IOException
    {
        if (createDirs)
        {
            File parent = file.getAbsoluteFile().getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }
        }
        DataOutputStream out = null;
        try
        {
            out = new DataOutputStream(new FileOutputStream(file));
            out.write(data);
        }
        finally
        {
            if (out != null) {
                out.close();
            }
        }
    }

    public static void writeFile(File file, byte[] data)
            throws IOException
    {
        writeFile(file, data, false);
    }

    public static boolean writeFileSafe(File file, byte[] data, boolean createDirs)
    {
        try
        {
            writeFile(file, data, createDirs);
            return true;
        }
        catch (IOException e) {}
        return false;
    }

    public static byte[] readFile(File file, long maxAllowedSize)
            throws IOException
    {
        long size = file.length();
        if ((maxAllowedSize >= 0L) && (size > maxAllowedSize)) {
            throw new IOException();
        }
        DataInputStream in = null;
        try
        {
            in = new DataInputStream(new FileInputStream(file));
            byte[] data = new byte[(int)size];
            in.readFully(data);
            return data;
        }
        finally
        {
            if (in != null) {
                in.close();
            }
        }
    }

    public static byte[] readFile(File file)
            throws IOException
    {
        return readFile(file, -1L);
    }

    public static byte[] readFile(String path)
            throws IOException
    {
        return readFile(new File(path), -1L);
    }

    public static byte[] readFileSafe(File file)
    {
        DataInputStream in = null;
        try
        {
            in = new DataInputStream(new FileInputStream(file));
            byte[] data = new byte[(int)file.length()];
            in.readFully(data);
            return data;
        }
        catch (IOException e)
        {
            byte[] arrayOfByte1;
            return new byte[0];
        }
        finally
        {
            if (in != null) {
                try
                {
                    in.close();
                }
                catch (IOException localIOException3) {}
            }
        }
    }

    public static byte[] readInputStream(InputStream in)
            throws IOException
    {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        byte[] data = new byte['?'];
        int n;
        while ((n = in.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, n);
        }
        buffer.flush();
        return buffer.toByteArray();
    }

    public static boolean isZIPFile(String path)
    {
        DataInputStream in = null;
        try
        {
            in = new DataInputStream(new FileInputStream(new File(path)));
            return (in.read() == 80) && (in.read() == 75) && (in.read() == 3) && (in.read() == 4);
        }
        catch (IOException e)
        {
            return false;
        }
        finally
        {
            if (in != null) {
                try
                {
                    in.close();
                }
                catch (IOException localIOException3) {}
            }
        }
    }

    public static boolean compressFolder(String folderPath, String zipfilePath)
    {
        File folder = new File(folderPath);
        if (!folder.isDirectory()) {
            return false;
        }
        ZipOutputStream out = null;
        try
        {
            out = new ZipOutputStream(new FileOutputStream(new File(zipfilePath)));
            compressFolderRecurse(out, folder, "/");
            return true;
        }
        catch (IOException e)
        {
            return false;
        }
        finally
        {
            if (out != null) {
                try
                {
                    out.close();
                }
                catch (IOException localIOException3) {}
            }
        }
    }

    private static void compressFolderRecurse(ZipOutputStream out, File base, String rpath)
            throws IOException
    {
        File f = new File(base, rpath);
        ZipEntry e;
        byte[] data;
        if (f.isFile())
        {
            e = new ZipEntry(rpath);
            out.putNextEntry(e);

            data = readFile(f.getAbsolutePath());
            out.write(data);
            out.closeEntry();
        }
        else if (f.isDirectory())
        {
            e = f.list();data = e.length;
            for (byte[] arrayOfByte1 = 0; arrayOfByte1 < data; arrayOfByte1++)
            {
                String name = e[arrayOfByte1];
                compressFolderRecurse(out, base, new File(rpath, name).getPath());
            }
        }
    }

    public static void extractToFolder(File inputZipFile, File outputFolder)
            throws IOException
    {
        ZipFile zip = new ZipFile(inputZipFile);Throwable localThrowable6 = null;
        try
        {
            for (e = zip.entries(); e.hasMoreElements();)
            {
                ZipEntry entry = (ZipEntry)e.nextElement();
                InputStream in = zip.getInputStream(entry);Throwable localThrowable7 = null;
                try
                {
                    byte[] data = readInputStream(in);
                    File f = new File(outputFolder, entry.getName());
                    writeFile(f, data, true);
                }
                catch (Throwable localThrowable1)
                {
                    localThrowable7 = localThrowable1;throw localThrowable1;
                }
                finally {}
            }
        }
        catch (Throwable localThrowable4)
        {
            Enumeration<? extends ZipEntry> e;
            localThrowable6 = localThrowable4;throw localThrowable4;
        }
        finally
        {
            if (zip != null) {
                if (localThrowable6 != null) {
                    try
                    {
                        zip.close();
                    }
                    catch (Throwable localThrowable5)
                    {
                        localThrowable6.addSuppressed(localThrowable5);
                    }
                } else {
                    zip.close();
                }
            }
        }
    }

    public static List<String> readLines(File file, Charset encoding)
            throws IOException
    {
        FileInputStream in = new FileInputStream(file);Throwable localThrowable3 = null;
        try
        {
            return readLines(in, encoding);
        }
        catch (Throwable localThrowable4)
        {
            localThrowable3 = localThrowable4;throw localThrowable4;
        }
        finally
        {
            if (in != null) {
                if (localThrowable3 != null) {
                    try
                    {
                        in.close();
                    }
                    catch (Throwable localThrowable2)
                    {
                        localThrowable3.addSuppressed(localThrowable2);
                    }
                } else {
                    in.close();
                }
            }
        }
    }

    public static List<String> readLines(InputStream input, Charset encoding)
            throws IOException
    {
        InputStreamReader isr = null;
        BufferedReader br = null;
        try
        {
            isr = new InputStreamReader(input, encoding);
            br = new BufferedReader(isr);
            List<String> lines = new ArrayList();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        }
        finally
        {
            if (br != null) {
                br.close();
            } else if (isr != null) {
                isr.close();
            }
        }
    }

    public static void writeLines(File file, List<String> lines, Charset encoding)
            throws IOException
    {
        FileOutputStream out = new FileOutputStream(file);Throwable localThrowable3 = null;
        try
        {
            writeLines(out, lines, encoding);
        }
        catch (Throwable localThrowable1)
        {
            localThrowable3 = localThrowable1;throw localThrowable1;
        }
        finally
        {
            if (out != null) {
                if (localThrowable3 != null) {
                    try
                    {
                        out.close();
                    }
                    catch (Throwable localThrowable2)
                    {
                        localThrowable3.addSuppressed(localThrowable2);
                    }
                } else {
                    out.close();
                }
            }
        }
    }

    public static void writeLines(OutputStream output, List<String> lines, Charset encoding)
            throws IOException
    {
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try
        {
            osw = new OutputStreamWriter(output, encoding);
            bw = new BufferedWriter(osw);
            for (String line : lines)
            {
                bw.write(line);
                bw.newLine();
            }
        }
        finally
        {
            if (bw != null) {
                bw.close();
            } else if (osw != null) {
                osw.close();
            }
        }
    }

    public static void copyFile(File src, File dst, boolean overwrite)
            throws IOException
    {
        if (!src.isFile()) {
            throw new FileNotFoundException("Source file not found: " + src);
        }
        if (dst.isDirectory()) {
            dst = new File(dst, src.getName());
        }
        if ((dst.exists()) && (!overwrite)) {
            throw new FileAlreadyExistsException("Copy would overwrite an existing file: " + dst);
        }
        InputStream in = new BufferedInputStream(new FileInputStream(src));Throwable localThrowable6 = null;
        try
        {
            OutputStream out = new BufferedOutputStream(new FileOutputStream(dst));Throwable localThrowable7 = null;
            try
            {
                copyStream(in, out, new byte['?']);
            }
            catch (Throwable localThrowable1)
            {
                localThrowable7 = localThrowable1;throw localThrowable1;
            }
            finally {}
        }
        catch (Throwable localThrowable4)
        {
            localThrowable6 = localThrowable4;throw localThrowable4;
        }
        finally
        {
            if (in != null) {
                if (localThrowable6 != null) {
                    try
                    {
                        in.close();
                    }
                    catch (Throwable localThrowable5)
                    {
                        localThrowable6.addSuppressed(localThrowable5);
                    }
                } else {
                    in.close();
                }
            }
        }
    }

    public static long copyStream(InputStream input, OutputStream output, byte[] buffer)
            throws IOException
    {
        long count = 0L;
        int n;
        while ((n = input.read(buffer)) != -1)
        {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public static String getRelativePath(File file, File base)
            throws IOException
    {
        String filename = file.getCanonicalPath();
        String basename = base.getCanonicalPath();
        if (!filename.startsWith(basename)) {
            return null;
        }
        String r = filename.substring(basename.length());
        r = Strings.ltrim(r, '/');
        r = Strings.ltrim(r, '\\');
        return r;
    }
}
