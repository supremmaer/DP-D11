
package forms;

import org.hibernate.validator.constraints.NotBlank;

public class TabooWordForm {

	private String	tabooWord;


	@NotBlank
	public String getTabooWord() {
		return this.tabooWord;
	}

	public void setTabooWord(final String tabooWord) {
		this.tabooWord = tabooWord;
	}

}
