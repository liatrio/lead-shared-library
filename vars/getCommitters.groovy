def call() {
  def log = "git log -n 5".execute()
  def emailLog  = log.text =~ /<(.*@.*)>/
  List<String> userEmailList = new ArrayList<>()
  println "inside getCommitters function"
  for(email in emailLog){
    userEmailList << email[1]
  }
  println userEmailList
  return userEmailList
}
