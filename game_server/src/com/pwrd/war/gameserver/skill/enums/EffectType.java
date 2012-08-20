package com.pwrd.war.gameserver.skill.enums;

/**
 * 效果类
 * 
 * @author zhutao
 * 
 */
public enum EffectType {
	A1    ("A1"),
	A2    ("A2"),
	A3    ("A3"),
	A4    ("A4"),
	A5    ("A5"),
	A6    ("A6"),
	A7    ("A7"),
	A8    ("A8"),
	A9    ("A9"),
	A10   ("A10"),
	A11   ("A11"),
	A12   ("A12"),
	A13   ("A13"),
	A14   ("A14"),
	A15   ("A15"),
	A16   ("A16"),
	A17   ("A17"),
	A18   ("A18"),
	A19   ("A19"),
	A20   ("A20"),
	A21   ("A21"),
	A22   ("A22"),
	A23   ("A23"),
	A24   ("A24"),
	A25   ("A25"),
	A26   ("A26"),
	A27   ("A27"),
	A28   ("A28"),
	A29   ("A29"),
	A30   ("A30"),
	A31   ("A31"),
	A32   ("A32"),
	A33   ("A33"),
	A34   ("A34"),
	A35   ("A35"),
	A36   ("A36"),
	A37   ("A37"),
	A38   ("A38"),
	A39   ("A39"),
	A40   ("A40"),
	A41   ("A41"),
	A42   ("A42"),
	A43   ("A43"),
	A44   ("A44"),
	A45   ("A45"),
	A46   ("A46"),
	A47   ("A47"),
	A48   ("A48"),
	A49   ("A49"),
	A50   ("A50"),
	A51   ("A51"),
	A52   ("A52"),
	A53   ("A53"),
	A54   ("A54"),
	A55   ("A55"),
	A56   ("A56"),
	A57   ("A57"),
	A58   ("A58"),
	A59   ("A59"),
	A60   ("A60"),
	A61   ("A61"),
	A62   ("A62"),
	A63   ("A63"),
	
	C1  ("C1") ,
	C2  ("C2") ,
	C3  ("C3") ,
	C4  ("C4") ,
	C5  ("C5") ,
	C6  ("C6") ,
	C7  ("C7") ,
	C8  ("C8") ,
	C9  ("C9") ,
	C10 ("C10") ,
	C11 ("C11") ,
	C12 ("C12") ,
	C13 ("C13") ,
	C14 ("C14") ,
	C15 ("C15") ,
	C16 ("C16"),
	C17 ("C17"),
	C18 ("C18"),
	
	;

	private String label;

	EffectType(String label) {
		this.label = label;
	}

	public static EffectType getEffectTypeByLabel(String label) {
		for (EffectType effectType : EffectType.values()) {
			if (effectType.label.equals(label)) {
				return effectType;
			}
		}
		return null;
	}
}
