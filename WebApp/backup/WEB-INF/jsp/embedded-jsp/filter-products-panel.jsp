<div style="text-align: center" class="row">
    <div class="col-centered">Sort by:</div>
    <div class="col-centered" style="width: 200px">
        <select id="filters" class="form-control" onchange="location = this.value;">
            <option value="/search?page=1<c:if test="${query != null}">&q=${query}</c:if>">newest</option>
            <option value="/search?page=1&by=price<c:if test="${query != null}">&q=${query}</c:if>">cheap first</option>
            <option value="/search?page=1&by=price&asc=no<c:if test="${query != null}">&q=${query}</c:if>">expensive first</option>
            <option value="/search?page=1&by=name<c:if test="${query != null}">&q=${query}</c:if>">alphabet</option>
            <option value="/search?page=1&by=name&asc=no<c:if test="${query != null}">&q=${query}</c:if>">alphabet desc</option>
        </select>
    </div>
</div>

<script>
    $("#filters :contains('${filter}')").first().prop("selected", true);
</script>