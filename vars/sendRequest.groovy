def call(requestBody) {
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
