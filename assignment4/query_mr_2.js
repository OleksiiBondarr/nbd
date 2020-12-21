var mapFunction = function() {
	for(var i = 0; i< this.credit.length; i++)
   		emit(this.credit[i].currency, {"balance":this.credit[i].balance});
};
var reduceFunction = function(k,v){
  res = {total:0.0};
  for(var i = 0; i< v.length; i++){
    res.total += parseFloat(v[i].balance);
  }
  return res;
};
var finalizeFunction = function(k,v){
  return v;
}
db.people.mapReduce(mapFunction, reduceFunction, {out: "res2",finalize:finalizeFunction});
printjson(db.res2.find({}).toArray());