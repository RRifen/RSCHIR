from django.http import HttpResponse


def index(request, name):
    return HttpResponse(f"Hello, {name}")


def special_case(request):
    return HttpResponse("Please enter your name in path")
