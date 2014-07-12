
import com.telerivet.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Test {
    
    public static void main(String[] args) {
                
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));        
                
        try
        {
            System.out.print("Enter your API key: ");
            String apiKey = br.readLine().trim();   
            TelerivetAPI tr = new TelerivetAPI(apiKey);
            
            APICursor<Project> cursor = tr.queryProjects();
            
            List<Project> projects = cursor.all();
            
            Project project;
            
            int numProjects = projects.size();
            
            if (numProjects > 1)
            {                
                for (int i = 0; i < numProjects; i++)
                {
                    System.out.println((i + 1) + ": " + projects.get(i).getName());
                }
                
                System.out.print("Choose a project (1-"+numProjects + "): ");
                String projectIndexStr = br.readLine();
                int projectIndex = Integer.parseInt(projectIndexStr) - 1;
                if (projectIndex < 0 || projectIndex >= numProjects)
                {
                    System.out.println("Invalid project index");
                    return;
                }
                
                project = projects.get(projectIndex);
            }
            else if (numProjects == 1)
            {
                project = projects.get(0);
            }
            else
            {
                System.out.println("You do not have any projects.");
                return;
            }
            
            System.out.print("Phone number to send message to: ");            
            String toNumber = br.readLine().trim();
            
            System.out.print("Message to send: ");
            String content = br.readLine();
            
            Message message = project.sendMessage(Util.options(
                "to_number", toNumber,
                "content", content
            ));            
            
            System.out.println("Message is " + message.getStatus() + ", message id=" + message.getId());            
        }
        catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }               
    }
}
