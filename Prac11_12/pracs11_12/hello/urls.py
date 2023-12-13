from django.urls import path

from . import views

urlpatterns = [
    path("<name>/", views.index, name="index"),
    path("", views.special_case, name="special_case")
]