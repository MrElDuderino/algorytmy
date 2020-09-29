
param n, integer, >= 2; /*liczba wierzchołków */

/*zbiór wierzchołków V i zbiów krawędzi E*/

set V, default {0..n};

set E, within V cross V;

/*pojemność krawędzi*/

param capacity{(i,j) in E}, > 0;

/*źródło*/
param s, symbolic, in V, default 0;

/*ujśćie*/
param t, symbolic, in V, != s, default n;

/*warunek: przepływ w krawędzi nie może być większy niż pojemność*/
var x{(i,j) in E}, >= 0, <= capacity[i,j];

var flow, >= 0;

/*suma przepływu wpływająca do wierzchołka musi być równa sumie przepływu wypływającego z wierzchołka*/
s.t. node{i in V}:

   sum{(j,i) in E} x[j,i] + (if i = s then flow)

   =
   
   sum{(i,j) in E} x[i,j] + (if i = t then flow);

maximize maxFlow: flow;

solve;

data;

