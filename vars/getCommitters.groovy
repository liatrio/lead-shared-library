def call() {
  def log = sh(returnStdout: true, script: "git --no-pager log -5")

//  def log = "git --no-pager log -5".execute()
//  log.waitFor()
  println "printing log below:"
  println log
  println "printing log.text below:"
  def var = log.text
  println var
  def emailLog  = var =~ /<(.*@.*)>/
  List<String> userEmailList = new ArrayList<>()
  for(email in emailLog){
    userEmailList << email[1]
  }
  println "printing userEmailList: "
  println userEmailList
  return userEmailList
}
