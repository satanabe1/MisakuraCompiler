package info.haxahaxa.compiler.misakura;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MisakuraConverterImpl implements MisakuraConverter {

	private List<KeyValue> misakuraWord;

	public MisakuraConverterImpl() {
		misakuraWord = new ArrayList<KeyValue>();
		misakuraWord.add(new KeyValue("ちいい", "ぢい゛い゛ぃ'"));
		misakuraWord.add(new KeyValue("すき", "ゅきいぃっ"));
		misakuraWord.add(new KeyValue("ぁ", "ぁぁ゛ぁ゛"));
		misakuraWord.add(new KeyValue("あ", "あああ あぉ"));
		misakuraWord.add(new KeyValue("お", "おﾞぉおォおん"));
		misakuraWord.add(new KeyValue("こん(にち|ばん)[はわ]", "こん$1みゃぁあ゛あ゛ぁ゛ぁぁあ！！"));
		misakuraWord.add(new KeyValue("えて", "えてへぇええぇﾞ"));
		misakuraWord.add(new KeyValue("する", "するぅ"));
		misakuraWord.add(new KeyValue("る[^ぅ]", "りゅ"));
		misakuraWord.add(new KeyValue("いい", "いぃぃっよぉおおﾞ"));
		misakuraWord.add(new KeyValue("して", "してぇぇぇぇ"));
		misakuraWord.add(new KeyValue("(いく|イク|行く)", "んはっ イっぐぅぅぅふうぅ"));
		misakuraWord.add(new KeyValue("ダメ", "んぉほぉぉォォ　らめぇ"));
		misakuraWord.add(new KeyValue("だめ", "らめぇぇ"));
		misakuraWord.add(new KeyValue("ちゃん", "ちゃぁん"));
		misakuraWord.add(new KeyValue("よ", "よお゛お゛お゛ぉ"));
		misakuraWord.add(new KeyValue("もう", "んもぉ゛お゛お゛ぉぉ"));
		misakuraWord.add(new KeyValue("(い|入)れて", "いれてえぇぇぇえ"));
		misakuraWord.add(new KeyValue("出る", "でちゃうっ れちゃうよぉおおﾞ"));
		misakuraWord.add(new KeyValue("でる", "でっ…でるぅでるうぅうぅ"));
		misakuraWord.add(new KeyValue("です", "れしゅぅぅぅ"));
		misakuraWord.add(new KeyValue("ます", "ましゅぅぅぅ"));
		misakuraWord.add(new KeyValue("ました", "ましゅたぁぁ"));
		misakuraWord.add(new KeyValue("はい", "はひぃ"));
		misakuraWord.add(new KeyValue("スゴイ", "スゴぉッ!!"));
		misakuraWord.add(new KeyValue("(凄|すご)い", "しゅごいのょぉぉぅ"));
		misakuraWord.add(new KeyValue("だ", "ら"));
		misakuraWord.add(new KeyValue("さ", "しゃ"));
		misakuraWord.add(new KeyValue("な", "にゃ"));
		misakuraWord.add(new KeyValue("つ", "ちゅ"));
		misakuraWord.add(new KeyValue("ちゃ", "ひゃ"));
		misakuraWord.add(new KeyValue("じ", "に゛ゃ"));
		misakuraWord.add(new KeyValue("ほ", "ほお゛お゛っ"));
		misakuraWord.add(new KeyValue("で", "れ"));
		misakuraWord.add(new KeyValue("す", "しゅ"));
		misakuraWord.add(new KeyValue("ざ", "じゃ"));
		misakuraWord.add(new KeyValue("せん", "せん゛ん゛"));
		misakuraWord.add(new KeyValue("せ", "しぇ"));
		misakuraWord.add(new KeyValue("い", "いぃ"));
		misakuraWord.add(new KeyValue("の", "のぉおお"));
	}

	@Override
	public String jap2misakura(String msg) {
		String message = String.copyValueOf(msg.toCharArray());
		Collections.shuffle(misakuraWord);
		for (KeyValue keyValue : misakuraWord) {
			message = message
					.replaceAll(keyValue.getKey(), keyValue.getValue());
		}
		return message;
	}

	@Override
	public String jap2misakura(String msg, boolean kana2hira) {
		// TODO Auto-generated method stub
		String message = String.copyValueOf(msg.toCharArray());
		if (kana2hira) {
			message = kata2hira(message);
		}
		return jap2misakura(message);
	}

	/**
	 * カタカナ→ひらがな変換
	 * 
	 * @param s
	 * @return
	 */
	private String kata2hira(String str) {
		StringBuilder sb = new StringBuilder(str);
		for (int i = 0; i < sb.length(); i++) {
			char c = sb.charAt(i);
			if (c >= 'ァ' && c <= 'ン') {
				sb.setCharAt(i, (char) (c - 'ァ' + 'ぁ'));
			} else if (c == 'ヵ') {
				sb.setCharAt(i, 'か');
			} else if (c == 'ヶ') {
				sb.setCharAt(i, 'け');
			} else if (c == 'ヴ') {
				sb.setCharAt(i, 'う');
				sb.insert(i + 1, '゛');
				i++;
			}
		}
		return sb.toString();
	}

	private class KeyValue {
		private String key;
		private String value;

		public KeyValue(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public String getValue() {
			return value;
		}
	}

}
