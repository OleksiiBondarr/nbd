var mapFunction = function() {
   emit(this.job,{});
};
var reduceFunction = function(k,v){
  return null;
};
var finalizeFunction = function(k,v){
  return v;
}
db.people.mapReduce(mapFunction, reduceFunction, {out: "res3",finalize:finalizeFunction});
printjson(db.res3.find({},{"_id":1}).toArray());