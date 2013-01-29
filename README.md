MisakuraCompiler
--
#### Q．何これ？
![Demo](https://raw.github.com/satanabe1/MisakuraCompiler/master/pic/demo1.png)  
コンパイルエラーをみさくら語で出力します．  
ヘルプだってみさくら語です．
![Demo](https://raw.github.com/satanabe1/MisakuraCompiler/master/pic/demo2.png)  
特別な事は何もできません．  

#### Q．どうやって？
コンパイルはMisakuraCompilerディレクトリでantを打てば通るはずです．  
javacでヤルなら，srcディレクトリに移動して，  
```
javac info/haxahaxa/compiler/misakura/Misakura.java
```  
こんな感じです？  
  
実行にはコマンドラインで，  
```
java info.haxahaxa.compiler.misakura.Misakura コンパイル引数...
```  
もしくは．  
```
java -jar misakura.jar コンパイル引数...
```  
です．  


謝辞
--
メッセージの変換は[みさくら語コンバータ](http://jet-black-laver.sakura.ne.jp/RTM/nankotu.htm "みさくらコンバータ")を利用しました．    
作者様に感謝します．  

誰かツンデレコンバータを作ってください．  


