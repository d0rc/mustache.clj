desc "Run unit test"
task :test => :javac do
  sh 'lein test'
end

desc "Install in local repository"
task :install_local => :test do
  sh 'lein install'
  sh 'cd ~/workspace/rssminer && lein deps'
end

desc "Install in clojars repository"
task :clojars => :test do
  sh 'rm *.jar pom.xml classes -rf && lein pom && lein jar '
  sh 'scp pom.xml *.jar clojars@clojars.org:'
end

desc "Javac"
task :javac do
  sh 'scripts/javac'
end

desc "lein swank"
task :swank => :javac  do
  sh "lein swank"
end
