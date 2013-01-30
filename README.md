MisakuraCompiler
--
#### Q．何これ？
![Demo](https://raw.github.com/satanabe1/MisakuraCompiler/master/pic/demo1.png)  
コンパイルエラーをみさくら語で出力します．  
ヘルプだってみさくら語です．
![Demo](https://raw.github.com/satanabe1/MisakuraCompiler/master/pic/demo2.png)  
特別な事は何もできません．  

#### Q．コンパイルは？
コンパイルはMisakuraCompilerディレクトリでantを打てば通るはずです．  
javacでヤルなら，srcディレクトリに移動して，  
```
javac info/haxahaxa/compiler/misakura/Misakura.java
```  
こんな感じです．  
  
#### Q．どうやるの？
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

ライセンス
--
ご自由にお使いください．
ということで、MITライセンスのテンプレそのままです．

#### The MIT License

Copyright (c) 2013 haxahaxa.info

Permission is hereby granted, free of charge, to any person
obtaining a copy of this software and associated documentation
files (the "Software"), to deal in the Software without
restriction, including without limitation the rights to use,
copy, modify, merge, publish, distribute, sublicense, and/or
sell copies of the Software, and to permit persons to whom the
Software is furnished to do so, subject to the following
conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR 
OTHER DEALINGS IN THE SOFTWARE.

