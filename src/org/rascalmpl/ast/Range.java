/*******************************************************************************
 * Copyright (c) 2009-2011 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:

 *   * Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI
 *   * Tijs van der Storm - Tijs.van.der.Storm@cwi.nl
 *   * Mark Hills - Mark.Hills@cwi.nl (CWI)
*******************************************************************************/

package org.rascalmpl.ast;


import org.eclipse.imp.pdb.facts.IConstructor;

import org.rascalmpl.interpreter.asserts.Ambiguous;

import org.eclipse.imp.pdb.facts.IConstructor;

import org.eclipse.imp.pdb.facts.IValue;

import org.rascalmpl.interpreter.IEvaluatorContext;

import org.rascalmpl.interpreter.Evaluator;


import org.rascalmpl.interpreter.env.Environment;

import org.rascalmpl.interpreter.matching.IBooleanResult;

import org.rascalmpl.interpreter.matching.IMatchingResult;

import org.rascalmpl.interpreter.result.Result;


public abstract class Range extends AbstractAST {
  public Range(IConstructor node) {
    super(node);
  }
  

  public boolean hasEnd() {
    return false;
  }

  public org.rascalmpl.ast.Char getEnd() {
    throw new UnsupportedOperationException();
  }

  public boolean hasStart() {
    return false;
  }

  public org.rascalmpl.ast.Char getStart() {
    throw new UnsupportedOperationException();
  }

  public boolean hasCharacter() {
    return false;
  }

  public org.rascalmpl.ast.Char getCharacter() {
    throw new UnsupportedOperationException();
  }


static public class Ambiguity extends Range {
  private final java.util.List<org.rascalmpl.ast.Range> alternatives;

  public Ambiguity(IConstructor node, java.util.List<org.rascalmpl.ast.Range> alternatives) {
    super(node);
    this.alternatives = java.util.Collections.unmodifiableList(alternatives);
  }

  @Override
  public Result<IValue> interpret(Evaluator __eval) {
    throw new Ambiguous((IConstructor) this.getTree());
  }
  
  @Override
  public org.eclipse.imp.pdb.facts.type.Type typeOf(Environment env) {
    throw new Ambiguous((IConstructor) this.getTree());
  }
  
  @Override
  public IBooleanResult buildBooleanBacktracker(IEvaluatorContext __eval) {
    throw new Ambiguous((IConstructor) this.getTree());
  }

  @Override
  public IMatchingResult buildMatcher(IEvaluatorContext __eval) {
    throw new Ambiguous((IConstructor) this.getTree());
  }
  
  public java.util.List<org.rascalmpl.ast.Range> getAlternatives() {
   return alternatives;
  }

  public <T> T accept(IASTVisitor<T> v) {
	return v.visitRangeAmbiguity(this);
  }
}





  public boolean isFromTo() {
    return false;
  }
  
static public class FromTo extends Range {
  // Production: sig("FromTo",[arg("org.rascalmpl.ast.Char","start"),arg("org.rascalmpl.ast.Char","end")])

  
     private final org.rascalmpl.ast.Char start;
  
     private final org.rascalmpl.ast.Char end;
  

  
public FromTo(IConstructor node , org.rascalmpl.ast.Char start,  org.rascalmpl.ast.Char end) {
  super(node);
  
    this.start = start;
  
    this.end = end;
  
}


  @Override
  public boolean isFromTo() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitRangeFromTo(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.Char getStart() {
        return this.start;
     }
     
     @Override
     public boolean hasStart() {
        return true;
     }
  
     @Override
     public org.rascalmpl.ast.Char getEnd() {
        return this.end;
     }
     
     @Override
     public boolean hasEnd() {
        return true;
     }
  	
}


  public boolean isCharacter() {
    return false;
  }
  
static public class Character extends Range {
  // Production: sig("Character",[arg("org.rascalmpl.ast.Char","character")])

  
     private final org.rascalmpl.ast.Char character;
  

  
public Character(IConstructor node , org.rascalmpl.ast.Char character) {
  super(node);
  
    this.character = character;
  
}


  @Override
  public boolean isCharacter() { 
    return true; 
  }

  @Override
  public <T> T accept(IASTVisitor<T> visitor) {
    return visitor.visitRangeCharacter(this);
  }
  
  
     @Override
     public org.rascalmpl.ast.Char getCharacter() {
        return this.character;
     }
     
     @Override
     public boolean hasCharacter() {
        return true;
     }
  	
}



}
