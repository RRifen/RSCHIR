from django.db import models


class Record(models.Model):
    books_count = models.IntegerField()
    phones_count = models.IntegerField()
    washing_machines_count = models.IntegerField()
    record_timestamp = models.DateTimeField(auto_now_add=True)
    image = models.ImageField(null=True, blank=True)
