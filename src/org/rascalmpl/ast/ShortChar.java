package org.rascalmpl.ast; 
import org.eclipse.imp.pdb.facts.INode; 
public abstract class ShortChar extends AbstractAST { 
  static public class Lexical extends ShortChar {
	private final String string;
         public Lexical(INode node, String string) {
		this.node = node;
		this.string = string;
	}
	public String getString() {
		return string;
	}

 	@Override
	public <T> T accept(IASTVisitor<T> v) {
     		return v.visitShortCharLexical(this);
  	}
} static public class Ambiguity extends ShortChar {
  private final java.util.List<org.rascalmpl.ast.ShortChar> alternatives;
  public Ambiguity(INode node, java.util.List<org.rascalmpl.ast.ShortChar> alternatives) {
	this.alternatives = java.util.Collections.unmodifiableList(alternatives);
         this.node = node;
  }
  public java.util.List<org.rascalmpl.ast.ShortChar> getAlternatives() {
	return alternatives;
  }
  
  @Override
public <T> T accept(IASTVisitor<T> v) {
     return v.visitShortCharAmbiguity(this);
  }
} @Override
public abstract <T> T accept(IASTVisitor<T> visitor);
}