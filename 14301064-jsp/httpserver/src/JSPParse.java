import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileManager;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class JSPParse {
	public String jsplist[];
	public String javalist[];
	public String contentType = "";
	public String importheader = "";

	public JSPParse() {

	}

	public void parse() {
		ReadJspFile();
		ReadJspJavaFile();
		IsTranslated();
	}

	public String[] ReadJspFile() {
		// 读取文件夹
		File file = new File(System.getProperty("user.dir") + "/jsp");
		// list()方法可以读取到当前文件的所有文件
		jsplist = file.list();
		for (int i = 0; i < jsplist.length; i++) {
			jsplist[i] = jsplist[i].substring(0, jsplist[i].indexOf(".jsp"));
		}
		return jsplist;
	}

	public void ReadJspJavaFile() {
		File file = new File(System.getProperty("user.dir") + "/jsp-java");
		// list()方法可以读取到当前文件的所有文件
		javalist = file.list();
		if (javalist.length == 0) {
		} else {
			for (int i = 0; i < javalist.length; i++) {
				javalist[i] = javalist[i].substring(0, javalist[i].indexOf(".java"));
			}
		}

	}

	public void IsTranslated() {
		if (javalist.length == 0) {
			for (int i = 0; i < jsplist.length; i++) {
				try {
					TranslateJspToJava(jsplist[i]);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			for (int i = 0; i < jsplist.length; i++) {
				int flag = 0;
				for (int j = 0; j < javalist.length; j++) {
					if (jsplist[i].equals(javalist[j])) {
						flag = 1;
					}
				}

				if (flag == 0) {
					try {
						TranslateJspToJava(jsplist[i]);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void TranslateJspToJava(String jspname) throws IOException {
		String path = System.getProperty("user.dir") + "/jsp/" + jspname + ".jsp";
		File jspfile = new File(path);
		BufferedReader reader = null;
		String content = "";
		try {
			reader = new BufferedReader(new FileReader(jspfile));
			String tempString = "";
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				if (tempString.contains("<%@")) {
					int index1 = tempString.indexOf("contentType=");
					String str = tempString.substring(index1 + 13, tempString.length());
					int index2 = str.indexOf('\"');
					contentType = "response.setContentType(" + '\"' + str.substring(0, index2 + 1) + ");";

					if (tempString.contains("import=")) {
						int index3 = tempString.indexOf("import=");
						String str1 = tempString.substring(index3 + 8, tempString.length());
						int index4 = str1.indexOf('\"');
						String importfile = str1.substring(0, index4);
						String[] s = importfile.split(",");
						for (int i = 0; i < s.length; i++) {
							s[i] = "import " + s[i] + ";";
							importheader += s[i];
						}
					}

				} else if (tempString.contains("<%=")) {
					tempString = tempString.replace("<%=", "$$out.println(");
					tempString = tempString.replace("%>", ");&&");
					content = content + " " + tempString;
				} else {
					content = content + " " + tempString;
				}

			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String Javacontent = "";
		Javacontent = getSerletCode(jspname, content);

		File dir = new File(System.getProperty("user.dir") + "/jsp-java"); // 临时目录
		if (!dir.exists()) {
			dir.mkdir();
		}

		String filename = jspname + ".java";
		File f = new File(System.getProperty("user.dir") + "/jsp-java/" + filename);

		if (!f.exists()) {
			f.createNewFile();
		}
		FileWriter fw = new FileWriter(f);

		fw.write(Javacontent);

		fw.close();

		// 取得当前系统的编译器
		JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
		// 获取一个文件管理器
		StandardJavaFileManager javaFileManager = javaCompiler.getStandardFileManager(null, null, null);
		// 文件管理器根与文件连接起来
		Iterable it = javaFileManager.getJavaFileObjects(new File(dir, filename));
		// 创建编译任务
		CompilationTask task = javaCompiler.getTask(null, javaFileManager, null, Arrays.asList("-d", "./jsp-java"),
				null, it);
		// 执行编译
		task.call();
		javaFileManager.close();

		File afile = new File(System.getProperty("user.dir") + "/jsp-java/" + jspname + ".class");
		afile.renameTo(new File(System.getProperty("user.dir") + "/bin/" + afile.getName()));
	}

	public String getSerletCode(String jspname, String jspcontent) throws IOException {
		String ServeletContent = "";
		File f1 = new File(System.getProperty("user.dir") + "/servletcode/" + "part_1.txt");
		File f2 = new File(System.getProperty("user.dir") + "/servletcode/" + "part_2.txt");
		File f3 = new File(System.getProperty("user.dir") + "/servletcode/" + "part_3.txt");
		File f4 = new File(System.getProperty("user.dir") + "/servletcode/" + "part_4.txt");
		File f5 = new File(System.getProperty("user.dir") + "/servletcode/" + "part_5.txt");
		BufferedReader br1, br2, br3, br4, br5;

		try {
			// 读part4
			br5 = new BufferedReader(new FileReader(f5));

			String line = null;

			String[] sign = new String[7];

			sign[0] = br5.readLine();
			sign[1] = "\n" + br5.readLine();
			sign[2] = sign[0] + br5.readLine();
			sign[3] = br5.readLine();
			sign[4] = br5.readLine();

			br5.close();
			//
			// 替换
			String ServeletContent1 = jspcontent.replace("$$out.println(", sign[2]);
			ServeletContent1 = ServeletContent1.replace(");&&", ");" + sign[1]);
			ServeletContent1 = ServeletContent1.replace("<%", sign[0]);
			ServeletContent1 = ServeletContent1.replace("%>", sign[1]);

			// import-class
			br1 = new BufferedReader(new FileReader(f1));

			line = null;

			ServeletContent = ServeletContent + importheader;
			ServeletContent = ServeletContent +br1.readLine();
			while ((line = br1.readLine()) != null) {

				ServeletContent = ServeletContent + "\n" + line;
			}

			br1.close();
			//

			ServeletContent = ServeletContent + "\n" + jspname + "\n";

			// 读part2
			br2 = new BufferedReader(new FileReader(f2));

			line = null;

			ServeletContent = ServeletContent + br2.readLine();

			while ((line = br2.readLine()) != null) {

				ServeletContent = ServeletContent + "\n" + line;
			}

			br2.close();
			//

			ServeletContent = ServeletContent + contentType;
			// part3
			br3 = new BufferedReader(new FileReader(f3));

			line = null;

			ServeletContent = ServeletContent + br3.readLine();

			while ((line = br3.readLine()) != null) {

				ServeletContent = ServeletContent + "\n" + line;
			}

			br3.close();
			//
			ServeletContent = ServeletContent + ServeletContent1;

			br4 = new BufferedReader(new FileReader(f4));

			ServeletContent = ServeletContent + br4.readLine();

			while ((line = br4.readLine()) != null) {

				ServeletContent = ServeletContent + "\n" + line;
			}

			br4.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ServeletContent;

	}

}
