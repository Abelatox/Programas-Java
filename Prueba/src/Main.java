import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
	public static void main(String[] args)
	{
		List<String> lines = Arrays.asList("Ext=.mtl","Lines which contain this will be removed:","Ns,Ke,Ni,illum,map_d");

		final File configFile = new File(".\\Deleter.ini");

		BufferedReader configReader = null;
		try {
			configReader = new BufferedReader(new FileReader(configFile));
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		BufferedWriter configWriter = null;
		try {
			configWriter = new BufferedWriter(new FileWriter(configFile,true));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(configFile.length() == 0)
		{
			for(int j = 0; j<lines.size();j++)
			{
				try {
					configWriter.write(lines.get(j));
					configWriter.newLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				configWriter.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		System.out.println(configFile.length());
		
		String extension = null;
		String line;
		String[] linesToRemove = null;
		try {
			line = configReader.readLine();
			extension = line.substring(line.indexOf("=")+1);
			System.out.println(extension);
			line = configReader.readLine();
			line = configReader.readLine();
			linesToRemove = line.split(",");
			for(int i=0; i<linesToRemove.length;i++)
			System.out.println(linesToRemove[i]);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		final File folder = new File(".");
		File file = null;
		File tempFile = null;
		BufferedReader reader = null;
		BufferedWriter tempWriter = null;
		BufferedReader tempReader = null;
		
		for (final File fileEntry : folder.listFiles()) {
				file = new File(fileEntry.getName());
				if(file.getName().contains(extension))
				{
					tempFile = new File("temp_"+file.getName());
					try{
						System.out.println(file.getAbsolutePath());
					    reader = new BufferedReader(new FileReader(file));
					    tempReader = new BufferedReader(new FileReader(tempFile));
					    tempWriter = new BufferedWriter(new FileWriter(tempFile));
					    String text = null;
					    int times=0;
					    
					    tempReader.mark((int) file.length());
					    while ((text = reader.readLine()) != null)
					    {
					    	times++;
	
					    	for(int i=0;i<linesToRemove.length;i++)
					    	{
						    	if(checkLines(text, linesToRemove))
						    	{
									System.out.println(times+": "+text.substring(0, text.indexOf(" "))+" found");
								}else{
									tempWriter.write(text + System.getProperty("line.separator"));
								}
						    }
					    }
					    tempWriter.flush();
					    tempReader.reset();
					 /*   while ((text = reader.readLine()) != null)
					    {
					    	
					    	//if()
					    }*/
					} catch (FileNotFoundException e) {
					    e.printStackTrace();
					} catch (IOException e) {
					    e.printStackTrace();
					} finally {
						try {
					        if (reader != null) {
					        	tempWriter.close(); 
					        	tempReader.close();
					        	reader.close();
					        	//Delete the original file
					            if (!file.delete()) {
					              System.out.println("Could not delete file");
					              return;
					            }
			
					            //Rename the new file to the filename the original file had.
					            if (!tempFile.renameTo(file))
					            {
					            	System.out.println("Could not rename file");
					            }
					        }
					    } catch (IOException e) {
					    	
					    }
					}
			}
		}
		//Ns, Ke, Ni, illum, map_d
	}
	
	static boolean checkLines(String text, String[] lines){
		for(int i=0; i<lines.length;i++)
		{
			if(text.startsWith(lines[i]))
			{
				return true;
			}
		}
		return false;
	}
	/*
	static void randomizador()
	{
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		System.out.println("Hola bienvenido al randomizador");
		String[] users = new String[100];
		boolean seguir = true;
		System.out.println(users.length);
		System.out.println("Introduce todos los usuarios:");
		int vueltas = 0;
		while(seguir)
		{
			String input = reader.next();
			if(input.equals(".")){
				break;
			}
			else{
				users[vueltas] = input;
				vueltas++;
			}
		}
		shuffleArray(users);
		for(int i = 0; i< users.length; i++)
		{
			if(users[i] != null)
			System.out.println(users[i]);
		}
	}
	
	static void shuffleArray(String[] ar)
	{
		Random rnd = ThreadLocalRandom.current();
	    for (int i = ar.length - 1; i > 0; i--)
	    {
	      int index = rnd.nextInt(i + 1);
	      // Simple swap
	      String a = ar[index];
	      ar[index] = ar[i];
	      ar[i] = a;
	    }
	 }
	 */
}
