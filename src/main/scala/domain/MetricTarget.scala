package domain

case class MetricTarget(name: String, prometheusQueryString: String, threshold: String)