import jenkins.model.*
import hudson.PluginWrapper
import java.util.logging.Logger

def logger = Logger.getLogger("plugin-installer")

// Wait for Jenkins to be fully ready
def waitForJenkinsReady() {
    while(true) {
        try {
            if(Jenkins.instance.pluginManager != null) {
                break
            }
            logger.info("Waiting for Jenkins to initialize...")
            Thread.sleep(5000)
        } catch(Exception e) {
            Thread.sleep(5000)
        }
    }
}

// Install plugins from file
def installPlugins() {
    def plugins = new File("/usr/share/jenkins/ref/plugins.txt").readLines()
        .findAll { it.trim().length() > 0 && !it.startsWith("#") }
    
    plugins.each { plugin ->
        def (name, version) = plugin.split(":")
        try {
            logger.info("Installing ${name} ${version ?: 'latest'}")
            def install = Jenkins.instance.updateCenter.getPlugin(name, version ?: 'latest').deploy()
            install.get()
        } catch(e) {
            logger.severe("Failed to install ${name}: ${e.message}")
        }
    }
    
    return plugins.size() > 0
}

// Main execution
waitForJenkinsReady()
def installed = installPlugins()

if (installed) {
    logger.info("Plugins installed, restarting...")
    Jenkins.instance.doSafeRestart()
} else {
    logger.info("No plugins needed installation")
}