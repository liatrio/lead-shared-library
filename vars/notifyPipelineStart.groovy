import groovy.json.JsonOutput

def call(params) {

    if (!params) params = [:]

    def request = params.request ? params.request : [:]

    request.type = 'pipeline-start'
    request.product = env.product
    request.url = env.BUILD_URL
    request.buildId = env.BUILD_ID
    request.gitUrl = env.GIT_URL
    request.gitCommit = env.GIT_COMMIT
    request.buildId = env.BUILD_ID

    request.branch = env.BRANCH_NAME

    def Jenkinsfile = params.Jenkinsfile ? params.Jenkinsfile : 'Jenkinsfile'
    def pipeline = readFile file: Jenkinsfile
    request.stageNames = getStageNames(pipeline)

    def requestBody = JsonOutput.toJson(request)
    def url = 'http://operator-jenkins.' + env.toolchainNamespace + '.svc.cluster.local:3000/pipeline-status';

    def post = new URL('http://operator-jenkins.' + env.toolchainNamespace + '.svc.cluster.local:3000/pipeline-status').openConnection();
    post.setRequestMethod('POST');
    post.setDoOutput(true);
    post.setConnectTimeout(5000);
    post.setReadTimeout(5000);
    post.setRequestProperty("Content-Type", "application/json");

    try {
      post.getOutputStream().write(requestBody.getBytes("UTF-8"));
      def postRC = post.getResponseCode();
      if(postRC.equals(200)) {
          println('Response: (' + postRC + ') ' + post.getInputStream().getText());
      }
    } catch(Exception ex) {
      println('Error sending request to endpoint: ' + ex);
    }
}

def getStageNames(pipeline){

  def names = []
  def lines = pipeline.readLines()

  for (int i = 0; i < lines.size(); i++){
    def line = lines[i]
    if (line.trim().size() == 0){}
    else {
      if (line.contains("stage(\'")){
        String [] tokens = line.split("\'");
        String stage = tokens[1];
        names.add(stage)
      }
      else if (line.contains("stage(\"")){
        String [] tokens = line.split("\"");
        String stage = tokens[1];
        names.add(stage)
      }
    }
  }

  return names
}
