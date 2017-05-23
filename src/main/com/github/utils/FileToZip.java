package github.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
/**
 * 
 * <br/>功能: 压缩文件
 * <br/>版本: 1.0
 * <br/>修改列表:
 */
public class FileToZip {
	public static void toZip(String oldFileName,String zipFileName) {
		ZipArchiveOutputStream zipOutput = null;
		InputStream in = null;
		try {
			String folderPath = oldFileName;
			File zipFile = new File(zipFileName);
			zipOutput = (ZipArchiveOutputStream) new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.ZIP,new FileOutputStream(zipFile));
			zipOutput.setEncoding("UTF-8");
			zipOutput.setUseZip64(Zip64Mode.AsNeeded);
			File[] files = new File(folderPath).listFiles();
			for (File file : files) {
				in = new FileInputStream(file);
				ZipArchiveEntry entry = new ZipArchiveEntry(file,file.getName());
				zipOutput.putArchiveEntry(entry);
				IOUtils.copy(in, zipOutput);
				zipOutput.closeArchiveEntry();
			}
			zipOutput.finish();
		} catch (Exception e) {
		}finally{
			
			try {
				if (in != null) {
					in.close();
				}
				if (zipOutput != null) {
					zipOutput.close();
				}
			} catch (Exception e1) {
			}
		}
	}
}
