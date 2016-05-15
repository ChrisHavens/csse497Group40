<ul>
<li ng-repeat="proj in results.hits.hits">
<span>{{proj._id}}</span>
<p>{{proj._source.name}}</p>
</li>
</ul>