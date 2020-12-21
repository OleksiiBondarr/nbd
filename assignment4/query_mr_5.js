var mapFunction = function() {
    if(this.nationality=="Poland" && this.sex=="Female"){
	for(var i = 0; i< this.credit.length; i++)
   		emit(this.credit[i].currency, {"balance":this.credit[i].balance});
    }
};
var reduceFunction = function(k,v){
  res = {total:0.0, count:0.0};
  for(var i = 0; i< v.length; i++){
    res.total += parseFloat(v[i].balance);
    res.count += 1;
  }
  return res;
};
var finalizeFunction = function(k,v){
  return {total:v.total, avg:v.total/v.count}
}
db.people.mapReduce(mapFunction, reduceFunction, {out: "res5",finalize:finalizeFunction});
printjson(db.res5.find({}).toArray());