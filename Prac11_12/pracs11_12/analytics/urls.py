from django.urls import path

from . import views

urlpatterns = [
    path("", views.analytics_detail, name="analytics")
]
