var mapFunction = function() {
   emit(this.nationality, {"height":this.height, "weight":this.weight,"count":1});
};
var reduceFunction = function(k,v){
  res = {bmi:[],count:0.0, sumBmi:0.0};
  for(var i = 0; i< v.length; i++){
    var parsedHeight = parseFloat(v[i].height);
    var tmpBmi = parseFloat(v[i].weight)/(parsedHeight*parsedHeight/10000);
    res.bmi.push(tmpBmi);
    res.sumBmi+=tmpBmi;
    res.count += 1;
  }
  return res;
};
var finalizeFunction = function(k,v){
  var avgVal = v.sumBmi/v.count;
  var res = {minBmi:avgVal, maxBmi:avgVal, avgBmi:avgVal};
  for(var i = 0; i< v.bmi.length; i++){
    curBmi = v.bmi[i];
    res.minBmi = curBmi<res.minBmi?curBmi:res.minBmi;
    res.maxBmi = curBmi>res.maxBmi?curBmi:res.maxBmi;
    }
  return res;
}
db.people.mapReduce(mapFunction, reduceFunction, {out: "res4",finalize:finalizeFunction});
printjson(db.res4.find({}).toArray());