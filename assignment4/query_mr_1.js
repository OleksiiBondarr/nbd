var mapFunction = function() {
   emit(this.sex, {"height":this.height, "weight":this.weight,"count":1});
};
var reduceFunction = function(k,v){
  res = {sumHeight:0.0, sumWeight:0.0,count:0.0,avgHeight:0.0,avgWeight:0.0};
  for(var i = 0; i< v.length; i++){
    res.sumHeight += parseFloat(v[i].height);
    res.sumWeight += parseFloat(v[i].weight);
    res.count += 1;
  }
  return res;
};
var finalizeFunction = function(k,v){
  v.avgHeight=v.sumHeight/v.count;
  v.avgWeight=v.sumWeight/v.count;
  return v;
}
db.people.mapReduce(mapFunction, reduceFunction, {out: "res",finalize:finalizeFunction});
printjson(db.res.find({}).toArray());