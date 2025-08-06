import jenkins.model.*
import hudson.slaves.*

def agentName = "docker-agent"
def instance = Jenkins.get()

if(!instance.getNode(agentName)) {
  def node = new DumbSlave(
    agentName,
    "/home/jenkins/agent",
    new JNLPLauncher()
  )
  instance.addNode(node)
  
  // Ensure secret file is created
  new File('/var/jenkins_home/agent-secret.txt').text = node.computer.jnlpMac
}