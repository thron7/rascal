module SymTable[&Name, &Value]

// A scope-oriented symbol table.
// Each scope consists of a map from names to values. 
// This is more intended to explore whether this can be expressed
// *at all* than that the datatype is well designed.

type rel[&Name, &Value] ScopeMap;
type int ScopeId;
data STable[&Name, &Value] stable(ScopeId scope, 
                                  rel[int, ScopeMap] scopes); 

//Create a new, empty,  table
STable[&Name, &Value] new_table(){
  return stable(0, {<0, {}>});
}

// Create a new, non-empty, table
STable[&Name, &Value] new_table(ScopeId scope, 
                              rel[int, ScopeMap] scopes){
  return stable(scope, scopes);
} 

// Update, in a given scope, the value of a variable
STable[&Name, &Value] update(STable[&Name, &Value] ST, 
                                 ScopeId scope, 
                                 &Name N, 
                                 &Value V){
  ST.scopes[scope] = V;
  return new_table(scope, ST.scopes);
}

// Get, in a given scope, the value of a variable
STable[&Name, &Value] value_of(STable[&Name, &Value] ST, 
                                ScopeId scope, 
                                &Name N){
//  return ST.scopes[scope][N];
  return ST.scopes[scope];
}

// update, in the current scope, the value of a variable
STable[&Name, &Value] update(STable[&Name, &Value] ST, 
                                 &Name N, 
                                 &Value V){
//  ST.scopes(scope)(N)= V;
  return new_table(scope, ST.scopes);
}

// Get, in the current scope, the value of a variable
STable value_of(STable[&Name, &Value] ST, 
                 &Name N){
  return ST.scopes[ST.scope][N];
}

// add a new scope and make it the current scope
STable[&Name, &Value] new_scope(STable[&Name, &Value] ST){
  ScopeId scope = ST.scope + 1;
  return new_table(scope, ST.scopes);
}

// switch to another scope
STable[&Name, &Value] switch_scope(STable[&Name, &Value] ST, 
                                       ScopeId scope){
  return new_table(scope, ST.scopes);
}