package exception;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Jialu Chen
 * @andrew_id jialuc
 */

public class AutoException extends Exception implements FixAuto {

	private static final long serialVersionUID = 2137441439449991446L;

	private int errno; //error number 
	private String name; //error name
	private String result; //fix strategy

	//get error name from exception enum
	public AutoException(EnumException exception) {
		this.errno = exception.getValue();
		this.name = exception.toString();
		log();
	}

	//more detailed tostring()
	public String toString() {
		StringBuffer string = new StringBuffer();
		string.append("error num:");
		string.append(this.errno);
		string.append("- error name: ");
		string.append(this.name);
		return string.toString();
	}

	public int getErrorNumber() {
		return errno;
	}

	//Log file 
	public void log() {
		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());
		StringBuffer log = new StringBuffer();
		log.append("errno ");
		log.append(this.errno);
		log.append(":");
		log.append(this.name);
		log.append("- ");
		log.append(ts.toString());
		log.append("\n");
		try {
			BufferedWriter bw = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream("logfile_exception.txt", true)));
			bw.write(log.toString());
			bw.flush();
			bw.close();
		} catch (IOException e) {
			System.out.println("Error: " + e.toString());
		}

	}

	//fix()
	public String fix(int errno) {
		Fix1to2 f1 = new Fix1to2();
		switch (errno) {
		case 1:
			try {
				result = f1.fixFileNotFound();
			} catch (Exception e) {
				result = e.toString();
			}
			break;
		case 2:
			result = f1.fixFileMissBasePrice();
		}
		return result;
	}

}
