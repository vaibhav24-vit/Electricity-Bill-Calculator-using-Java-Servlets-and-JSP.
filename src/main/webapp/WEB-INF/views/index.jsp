<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="theme-color" content="#f5f7f3">
    <title>Electricity bill calculator</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles.css">
</head>
<body>
<header class="topbar">
    <a class="brand" href="${pageContext.request.contextPath}/bill" aria-label="Wattwise home">
        <span class="brand-mark" aria-hidden="true">w</span>
        <span>Wattwise</span>
    </a>
    <span class="topbar-note"><span class="status-dot"></span> Simple energy estimate</span>
</header>

<main class="page-shell">
    <section class="intro" aria-labelledby="page-title">
        <p class="eyebrow">YOUR MONTHLY USAGE, MADE CLEAR</p>
        <h1 id="page-title">Know what your<br class="desktop-break"> power will cost.</h1>
        <p class="intro-copy">Enter your electricity usage for a quick estimate, calculated slab by slab.</p>
    </section>

    <section class="calculator" aria-label="Electricity bill calculator">
        <div class="calculator-main">
            <div class="section-heading">
                <div>
                    <p class="eyebrow">BILL ESTIMATOR</p>
                    <h2>Calculate your bill</h2>
                </div>
                <span class="meter-icon" aria-hidden="true">↗</span>
            </div>

            <form action="${pageContext.request.contextPath}/bill" method="post">
                <label for="units">Electricity consumed</label>
                <div class="input-wrap">
                    <input id="units" name="units" type="number" min="0" step="1" inputmode="numeric"
                           placeholder="e.g. 180" value="<c:out value='${unitsInput}'/>"
                           aria-describedby="units-help" required>
                    <span class="input-unit">units</span>
                </div>
                <p class="field-help" id="units-help">Enter your total units for this billing period.</p>
                <c:if test="${not empty error}">
                    <p class="error-message" id="units-error" role="alert"><c:out value="${error}"/></p>
                </c:if>
                <button type="submit">Calculate bill <span aria-hidden="true">→</span></button>
            </form>

            <c:if test="${not empty bill}">
                <section class="result" aria-live="polite" aria-label="Bill estimate">
                    <div class="result-top">
                        <div>
                            <p class="eyebrow">ESTIMATED TOTAL</p>
                            <p class="result-amount">₹<c:out value="${bill.total}"/></p>
                        </div>
                        <span class="result-units"><c:out value="${bill.units}"/> units</span>
                    </div>
                    <div class="breakdown">
                        <p class="breakdown-title">Charge breakdown</p>
                        <c:forEach var="slab" items="${bill.slabs}">
                            <div class="breakdown-row">
                                <span><c:out value="${slab.label}"/> <small><c:out value="${slab.units}"/> × ₹<c:out value="${slab.rate}"/></small></span>
                                <strong>₹<c:out value="${slab.charge}"/></strong>
                            </div>
                        </c:forEach>
                        <div class="breakdown-row breakdown-total">
                            <span>Total energy charge</span>
                            <strong>₹<c:out value="${bill.total}"/></strong>
                        </div>
                    </div>
                </section>
            </c:if>
        </div>

        <aside class="tariff-panel" aria-labelledby="tariff-title">
            <div class="tariff-heading">
                <div>
                    <p class="eyebrow">THE RATE CARD</p>
                    <h2 id="tariff-title">Pay by the slab</h2>
                </div>
                <span class="bolt-icon" aria-hidden="true">ϟ</span>
            </div>
            <p class="tariff-copy">Each rate applies only to the units in that usage range.</p>
            <ul class="tariff-list">
                <li><span><i class="slab-marker marker-one"></i>Units 1–50</span><strong>₹3.50 <small>/ unit</small></strong></li>
                <li><span><i class="slab-marker marker-two"></i>Units 51–150</span><strong>₹4.00 <small>/ unit</small></strong></li>
                <li><span><i class="slab-marker marker-three"></i>Units 151–250</span><strong>₹5.20 <small>/ unit</small></strong></li>
                <li><span><i class="slab-marker marker-four"></i>Above 250 units</span><strong>₹6.50 <small>/ unit</small></strong></li>
            </ul>
            <div class="note">
                <span class="note-icon" aria-hidden="true">i</span>
                <p>This estimate includes energy charges only. Taxes and other utility fees are not included.</p>
            </div>
            <div class="illustration" aria-hidden="true">
                <span class="sun"></span>
                <span class="hill hill-back"></span>
                <span class="hill hill-front"></span>
                <span class="house"><i></i></span>
                <span class="tree tree-one"></span>
                <span class="tree tree-two"></span>
            </div>
        </aside>
    </section>
    <footer>Made for a brighter, more informed home <span aria-hidden="true">✳</span></footer>
</main>
</body>
</html>
