# Clojure-12-factor-app

When I learnt clojure, I of course had this strong desire to introduce it into my workplace. Now this is a long journey and still is. So I started thinking of the prep-work I will require to do apart if I really need to make it to production eventually. Apart from convincing my managers and artitects to say yes, there is a lot of work needed to achieve. I work in java shop company. I somehow wanted clojure to make an entry not quietly, not so costly and let others discover its advantages to gain wide range acceptance. So this is an effort to make a production quality template and I thought classic 12-factor should do me good.     

## _Codebase - Git_

We already use Stash. This template is hosted as git repository. If you use anything other than git, please don't. 

## _Dependencies - Leiningen and Maven_

I come from Java world and maven has served me well but when it comes to clj I am fond of Leiningen for its native Clojure appeal.

Sneaking clojure into a java app is one thing but building a clojure project, publishing and maintaining artifacts into your eco system is another matter altogether. I work in a java shop company. We use maven to build and release. So if I have to introduce clojure project into the company then I somehow need to convince the configuration engineers to install, configure Leiningen on all the Jenkins servers. This can be time consuming and involves a lot of explaining and everyone to learn the new DSL and how to do configurations etc and somehow I wanted a faster way. So I did the same that I did with Clojure. I snuck clojure into java app and started using Interop and Persistent data structures. Why not do the same with Lein. Use maven to do lein project as use Java did for clojure.

lein pom

## _Config - properties and edn_

If you don't want to disrupt your java .properties files based mechanism then I have used a small library that takes care of converting properties to a clojure map and their right types instead of everything as string as it will do by default. Library called  'propertea' and I checked in black-duck its approved without any vulnerabilities. 

If you don't have such a limitation that you have to use java properties file approach then I will strongly recommened Clojure's edn. The data format used in Datomic.


## _Disposability - stuart sierra's component or Anatoly Polinsky's mount_

One of the troubling aspect to me for a long time was how to maintain state in a big clojure app. What is the right way? I looked a lot and try to come up with approaches which eventually made my app messy. Then one day I stumbled on a talk by Stuart Sierra called "Component Just Enough Structure". The talk made complete sense and I tried his approach and it works. Before that I was confused on how my design is in big clojure app is going to be and how I can achieve passing state everywhere. This tiny framework makes your program state easy to reason with, visible and start/stop at will. And it does it so with Object where state is visible, that's a good call! and without losing any of the clojure principles like immutability and functional programming.

link: https://github.com/stuartsierra/component

Second famous choice I am evaluating Mount which approaches it a bit differently. I will make a choice after spending enough time with it. So far I really liked Mount which doesn't need a complete buy-in of component approach. Component is something I have done similar or people do in Spring application where everything is explicitely injected while mount is Singletons. 

So far I have included both in here.


## _Logs - clj logging and logback_

Configured clojure tools logging and logback.

## License

Copyright © 2017 Manoj Arya 

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
