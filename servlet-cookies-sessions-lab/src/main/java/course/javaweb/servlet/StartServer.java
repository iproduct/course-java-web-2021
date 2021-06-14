package course.javaweb.servlet;

import org.codehaus.cargo.container.InstalledLocalContainer;
import org.codehaus.cargo.container.configuration.LocalConfiguration;
import org.codehaus.cargo.container.property.User;
import org.codehaus.cargo.container.tomcat.Tomcat9xInstalledLocalContainer;
import org.codehaus.cargo.container.tomcat.Tomcat9xStandaloneLocalConfiguration;
import org.codehaus.cargo.container.tomcat.TomcatPropertySet;
import org.codehaus.cargo.container.tomcat.TomcatWAR;
import org.codehaus.cargo.util.log.LogLevel;
import org.codehaus.cargo.util.log.Logger;
import org.codehaus.cargo.util.log.SimpleLogger;

import java.net.MalformedURLException;
import java.util.Scanner;

public class StartServer {
    public static void main(String[] args) throws InterruptedException, MalformedURLException {
        TomcatWAR war = new TomcatWAR("./target/invoicing-servlet-lab-1.0-SNAPSHOT");
        war.setContext("invoicing");
//        Deployable war = new WAR("./target/invoicing-servlet-lab-1.0-SNAPSHOT.war");

        LocalConfiguration configuration =
                new Tomcat9xStandaloneLocalConfiguration("target/tomcat9x");
        configuration.addDeployable(war);
        User u = new User();
        u.setName("tomcat");
        u.setPassword("tomcat");
        u.addRole("manager-gui");
        u.addRole("admin-gui");
        configuration.addUser(u);
        configuration.getLogger().setLevel(LogLevel.DEBUG);
        configuration.setProperty(TomcatPropertySet.CONTEXT_RELOADABLE, "true");
        configuration.setProperty(TomcatPropertySet.COPY_WARS, "false");

        InstalledLocalContainer container =
                new Tomcat9xInstalledLocalContainer(configuration);
        container.setHome("D:\\Course_Java_Web_Development\\apache-tomcat-9.0.46");
        Logger log = new SimpleLogger();
        container.setLogger(log);
        //        EmbeddedLocalContainer container =
//                new Tomcat9xEmbeddedLocalContainer(configuration);

        container.start();
        // Here you are assured the container is started.
        System.out.println("Server started ...\nHit a <Enter> to stop.");
//        Deployer deployer = new TomcatCopyingInstalledLocalDeployer(container);
//        deployer.deploy(war);

// Here you are NOT sure the WAR has finished deploying. To be sure you
// need to use a DeployableMonitor to monitor the deployment. For example
// the following code deploys the WAR and wait until it is available to
// serve requests (the URL should point to a resource inside your WAR):
//        deployer.deploy(war, new URLDeployableMonitor(
//                new URL("http://localhost:8080/shop")));

        new Scanner(System.in).nextLine();
        System.out.println("Stopping server ...");
        container.stop();
        // Here you are assured the container is stopped.
        System.out.println("Server stopped ...");


    }
}
