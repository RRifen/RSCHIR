import datetime
import io
import json

from django.core.files.base import ContentFile
from django.http import JsonResponse, HttpResponse
from django.views.decorators.csrf import csrf_exempt
from matplotlib import pyplot as plt
from django.utils import timezone

from analytics.models import Record


# Create your views here.
@csrf_exempt
def analytics_detail(request):
    if request.method == 'POST':
        return analytics_post(request)
    else:
        return analytics_get(request)


def analytics_get(request):
    try:
        timestamp_str = request.GET.get('time',
                                        (timezone.now() + timezone.timedelta(hours=3)).strftime('%d/%m/%Y_%H:%M:%S'))
        print(timestamp_str)
        timestamp = datetime.datetime.strptime(timestamp_str, '%d/%m/%Y_%H:%M:%S') - timezone.timedelta(hours=3)
    except ValueError:
        return HttpResponse(status=400)

    record = Record.objects.filter(record_timestamp__lte=timestamp).order_by('-record_timestamp').first()
    if not record:
        return HttpResponse(status=400)
    if record.image:
        with open(record.image.path, "rb") as f:
            return HttpResponse(f.read(), content_type="image/jpeg")
    else:
        labels = f'Books {record.books_count}', f'Phones {record.phones_count}', f'Washing Machines {record.washing_machines_count}'
        sizes = [record.books_count, record.phones_count, record.washing_machines_count]
        fig, ax = plt.subplots()
        ax.pie(sizes, labels=labels, autopct='%1.1f%%')
        f = io.BytesIO()
        plt.savefig(f)
        content_file = ContentFile(f.getvalue())
        record.image.save(f'plot_{record.id}.jpeg', content_file)
        record.save()
        with open(record.image.path, "rb") as f:
            return HttpResponse(f.read(), content_type="image/jpeg")


def analytics_post(request):
    data = json.loads(request.body)
    if 'object' in data:
        obj = data['object']
        match obj:
            case 'phone':
                record = retrieve_record()
                record.phones_count = record.phones_count + 1
                record.save()
            case 'washing_machine':
                record = retrieve_record()
                record.washing_machines_count = record.washing_machines_count + 1
                record.save()
            case 'book':
                record = retrieve_record()
                record.books_count = record.books_count + 1
                record.save()
            case _:
                return HttpResponse(status=400)
        return HttpResponse(status=200)
    else:
        return HttpResponse(status=400)


def create_empty_record():
    record = Record()
    record.books_count = 0
    record.phones_count = 0
    record.washing_machines_count = 0
    return record


def retrieve_record():
    record = Record.objects.filter(record_timestamp__lte=timezone.now()).order_by(
        '-record_timestamp').first()
    if record is None:
        record = create_empty_record()
    record.record_timestamp = timezone.now()
    print(timezone.now())
    record.image = None
    record.pk = None
    return record
