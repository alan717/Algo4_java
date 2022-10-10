
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipUnpacker
{
    ZipFile zipfile;
    String basedir;
    InstallerContext ctx;
    String skipPrefix;
    List<String> errorpaths = new ArrayList();

    public static class Record
    {
        private String name;
        private int code;

        Record(String name, int code)
        {
            this.name = name;
            this.code = code;
        }

        public String getName()
        {
            return this.name;
        }

        public int getCode()
        {
            return this.code;
        }
    }

    List<Record> records = new ArrayList();

    public ZipUnpacker(ZipFile zipfile, String basedir, InstallerContext ctx)
    {
        this.zipfile = zipfile;
        this.basedir = basedir;
        this.ctx = ctx;
    }

    public ZipUnpacker(ZipFile zipfile, String basedir)
    {
        this(zipfile, basedir, null);
    }

    public List<String> getErrorPaths()
    {
        return this.errorpaths;
    }

    public List<Record> getRecords()
    {
        return this.records;
    }

    public void setSkipPrefix(String skipPrefix)
    {
        this.skipPrefix = skipPrefix;
    }

    public boolean unzip()
    {
        Enumeration<? extends ZipEntry> all = this.zipfile.entries();
        for (;;)
        {
            if (all.hasMoreElements())
            {
                ZipEntry entry = (ZipEntry)all.nextElement();
                if (entry.isDirectory()) {
                    continue;
                }
                String name = entry.getName();
                if ((this.skipPrefix != null) && (name != null) && (!name.startsWith(this.skipPrefix))) {
                    continue;
                }
                InputStream in = null;
                try
                {
                    in = this.zipfile.getInputStream(entry);
                    extractEntry(in, name);
                    if (in != null) {
                        try
                        {
                            in.close();
                        }
                        catch (IOException e)
                        {
                            Logger.logd(e);
                        }
                    }
                }
                catch (IOException e)
                {
                    Logger.logd(e);
                    this.errorpaths.add(name);
                }
                finally
                {
                    if (in != null) {
                        try
                        {
                            in.close();
                        }
                        catch (IOException e)
                        {
                            Logger.logd(e);
                        }
                    }
                }
            }
        }
        return this.errorpaths.isEmpty();
    }

    private byte[] readInputStream(InputStream in)
            throws IOException
    {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        byte[] data = new byte[131072];
        int n;
        while ((n = in.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, n);
        }
        buffer.flush();
        return buffer.toByteArray();
    }

    private void extractEntry(InputStream in, String name)
            throws IOException
    {
        byte[] data = readInputStream(in);
        writeEntry(name, data, true);
    }

    private boolean writeEntry(String name, byte[] data, boolean createDirs)
    {
        File file = new File(this.basedir, name);
        if (createDirs)
        {
            File parent = file.getAbsoluteFile().getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }
        }
        int resultcode = 1;
        if ((file.exists()) && (file.isFile())) {
            resultcode = 2;
        }
        try
        {
            if (this.ctx != null) {
                this.ctx.writeFile(file, data);
            } else {
                IO.writeFile(file, data, true);
            }
            this.records.add(new Record(name, resultcode));
            return true;
        }
        catch (IOException e)
        {
            Logger.logd(e);
            this.records.add(new Record(name, -resultcode));
        }
        return false;
    }
}
