var dataSet = [
  {
    "hisid": 2131,
    "wyyid": 3220012996,
    "musics": {
      "mid": 409649818,
      "mname": "牵丝戏【合唱】（Cover 银临 / Aki阿杰）",
      "style": null,
      "times": null,
      "singers": [
        {
          "singid": 487,
          "mid": 409649818,
          "sid": 12025552,
          "sname": "双笙"
        }
      ]
    },
    "playcount": 0,
    "score": 100
  },
  {
    "hisid": 2132,
    "wyyid": 3220012996,
    "musics": {
      "mid": 453003622,
      "mname": "外婆桥（Cover 初音ミク）",
      "style": null,
      "times": null,
      "singers": [
        {
          "singid": 490,
          "mid": 453003622,
          "sid": 12025552,
          "sname": "双笙"
        }
      ]
    },
    "playcount": 0,
    "score": 85
  },
  {
    "hisid": 2133,
    "wyyid": 3220012996,
    "musics": {
      "mid": 857896,
      "mname": "A Little Story",
      "style": null,
      "times": null,
      "singers": [
        {
          "singid": 488,
          "mid": 857896,
          "sid": 22247,
          "sname": "Valentin"
        }
      ]
    },
    "playcount": 0,
    "score": 85
  },
  {
    "hisid": 2134,
    "wyyid": 3220012996,
    "musics": {
      "mid": 33241113,
      "mname": "天边外",
      "style": null,
      "times": null,
      "singers": [
        {
          "singid": 492,
          "mid": 33241113,
          "sid": 961358,
          "sname": "不才"
        }
      ]
    },
    "playcount": 0,
    "score": 85
  },
  {
    "hisid": 2135,
    "wyyid": 3220012996,
    "musics": {
      "mid": 35416253,
      "mname": "她",
      "style": null,
      "times": null,
      "singers": [
        {
          "singid": 486,
          "mid": 35416253,
          "sid": 961358,
          "sname": "不才"
        }
      ]
    },
    "playcount": 0,
    "score": 71
  },
  {
    "hisid": 2136,
    "wyyid": 3220012996,
    "musics": {
      "mid": 28798308,
      "mname": "一身诗意千寻瀑",
      "style": null,
      "times": null,
      "singers": [
        {
          "singid": 491,
          "mid": 28798308,
          "sid": 961358,
          "sname": "不才"
        }
      ]
    },
    "playcount": 0,
    "score": 71
  },
  {
    "hisid": 2137,
    "wyyid": 3220012996,
    "musics": {
      "mid": 33211676,
      "mname": "Hello",
      "style": null,
      "times": null,
      "singers": [
        {
          "singid": 489,
          "mid": 33211676,
          "sid": 381949,
          "sname": "OMFG"
        }
      ]
    },
    "playcount": 0,
    "score": 57
  },
  {
    "hisid": 2138,
    "wyyid": 3220012996,
    "musics": {
      "mid": 32897793,
      "mname": "拂雪(纯歌版)",
      "style": null,
      "times": null,
      "singers": [
        {
          "singid": 494,
          "mid": 32897793,
          "sid": 961358,
          "sname": "不才"
        }
      ]
    },
    "playcount": 0,
    "score": 57
  },
  {
    "hisid": 2139,
    "wyyid": 3220012996,
    "musics": {
      "mid": 448184048,
      "mname": "化身孤岛的鲸",
      "style": null,
      "times": null,
      "singers": [
        {
          "singid": 379,
          "mid": 448184048,
          "sid": 961358,
          "sname": "不才"
        }
      ]
    },
    "playcount": 0,
    "score": 57
  },
  {
    "hisid": 2140,
    "wyyid": 3220012996,
    "musics": {
      "mid": 35307082,
      "mname": "倾尽天下",
      "style": null,
      "times": null,
      "singers": [
        {
          "singid": 495,
          "mid": 35307082,
          "sid": 10089,
          "sname": "司夏"
        }
      ]
    },
    "playcount": 0,
    "score": 57
  }
];



$(function () {
  $('#example1').DataTable({
    data: dataSet,
    columns: [
      { data: "hisid" },
      { data: "musics.mname" },
      { data: "musics.singers[0].sname" },
    ]
  })
  $('#example2').DataTable({
    'paging': true,
    'lengthChange': false,
    'searching': false,
    'ordering': true,
    'info': true,
    'autoWidth': false
  })
})