import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class CSVToJSONConverter {

	static ObjectMapper mapper = new ObjectMapper();
	private static ArrayList<List<String>> ReadData(String inputFile) throws IOException {
		ArrayList<List<String>> Paths = new ArrayList<List<String>>();
		try {
		BufferedReader br = new BufferedReader(new FileReader(inputFile));
		ArrayList<String> HeaderList  = new ArrayList<String>();
		int HeaderCount = 0;
		String line = "";
		String Split = ",";

		
		// Look for first header
		while (true) {
			line = br.readLine();
			String[] Data = line.split(Split);
			// if Parent-Group line is found
			if (Data[0].equals("Parent-Group")) {
				
				for(int i = 1; i < Data.length; i++)
				{
					HeaderList.add(Data[i]);
				}
				// Get out of while loop
				break;
				}
			}	
		System.out.println("First header is " + HeaderList.get(0));

		// Look for the headers line
		while (true) {
			line = br.readLine();
			String Data[] = line.split(Split);
			if (Data.length == 0)
				continue;
			if (Data[0].equals(HeaderList.get(0))) {
				System.out.println("Header Line is found");
				break;
				}
		}
		
		   
		while ((line = br.readLine()) != null) 
		{
			List<String> Path = new ArrayList<String>();
			String Data[] = line.split(Split);
			for(int i = 0; i < HeaderList.size(); i++)
			{
				Path.add(Data[i]);
			}
			Path.add(Data[Data.length-1]);
			Paths.add(Path);
		}

		}
		catch(FileNotFoundException  e) {
				e.printStackTrace();
				System.err.println("File not found");
		}
		return Paths;
	}
	
	
	
	
	private static JsonNode parse(ArrayList<List<String>> PathsList) throws IOException {
		JsonNode Root = mapper.createObjectNode();
		((ObjectNode) Root).put("name","Root");
		List<String> CurrentPath = PathsList.get(0);
		Double totalvalue = (double) 0;
		JsonNode tmp = Root;
		System.out.println(CurrentPath.get(0));
//		ObjectNode newNode = mapper.createObjectNode();
//		((ObjectNode) newNode).put("name", CurrentPath.get(0));
//		((ObjectNode) Root).set("children", newNode);
		for(int g = 0; g < PathsList.size() -1; g++)
		{
    	CurrentPath = PathsList.get(g); 
		for(int i = 0; i< CurrentPath.size()-1;i++)
		{
			String t = CurrentPath.get(i);
			int indicator = i;
			ArrayNode childnode = mapper.createArrayNode();
			String chi = tmp.path("children").toString();
			if(!"".equals(chi))			
			{childnode = (ArrayNode) tmp.path("children");}
			ObjectNode newNode = mapper.createObjectNode();
			if (!CurrentPath.get(i).equals(tmp.path("children").toString())) 
			{
			((ObjectNode) newNode).put("name", CurrentPath.get(i));
			 childnode.add(newNode);
			 if(!tmp.isArray())
			{
				 ((ObjectNode) tmp).set("children", childnode);
				 tmp = tmp.path("children");			
			}
			 else
			 {
				 for(int h = 0; h < tmp.size();h++)
				 {
					 if((tmp.get(h).path("name").asText()).equals(CurrentPath.get(indicator-1)))
					 {
						 ObjectNode child = (ObjectNode) tmp.get(h);
						 child.set("children",childnode);
						 tmp = child;
						 break;
					 }
				 }				 
			 }
			}			
		}
		tmp = null;
		tmp = Root;
		}
//		for(int i = 0; i < PathsList.size() -1; i++)
//		{
//			List<String> temp = PathsList.get(i);
//			
//		}
//		for(int i = 0; i < CurrentPath.size() -1; i++)
//		{
//			System.out.println(CurrentPath.get(i));
//			
//		}
//			//current path from root
//			if (/* a newpath */ ) {
//				addPath();
//				//add new path to the "tree"				
//				//update current path
//			}
//		}
		
		return Root;
	}
//	
//	private void addPath(String path, JsonNode root) {
//		// TODO Auto-generated method stub
//		
//	}

	public static void main(String[] args) throws IOException {
		// check if there isn't an input
		if (args.length <= 0) {
			System.err.println("Please input a csv file name");
		}
		// if there is an input
		else {
			// declare variables
			String inputFile = args[0];;	
			try {
				JsonNode Root = parse(ReadData(inputFile));
				String resultUpdate = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(Root);
				System.out.println(resultUpdate);
				System.out.println("JsonList finished");
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("File not found");
			}
		}
	}
}
